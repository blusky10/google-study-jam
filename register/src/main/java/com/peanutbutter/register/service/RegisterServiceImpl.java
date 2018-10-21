package com.peanutbutter.register.service;

import com.peanutbutter.register.dto.RequestObj;
import com.peanutbutter.register.dto.ResponseObj;
import com.peanutbutter.register.entity.User;
import com.peanutbutter.register.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestService restService;

    @Autowired
    private Environment environment;

    @Override
    public User register(User user) {

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        LOGGER.debug("[Register-Service] userExists : " + userExists);

        if (userExists.isPresent()) {
            return null;
        }

        user.setEnabled(false);
        user.setToken(UUID.randomUUID().toString());
        User registerUser = userRepository.save(user);

        RequestObj trySendMail = trySendMail(registerUser);

        List<ResponseObj> responseObjs = restService.doTry(Arrays.asList(trySendMail));

        restService.confirmAll(responseObjs);

        return registerUser;
    }

    @Override
    public User confirm(User user, String password) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPassword(password);
        user.setEnabled(true);

        // Save user
        User registerUser = userRepository.save(user);

        return registerUser;
    }


    private RequestObj trySendMail(User user)  {

        String mailServiceUrl = environment.getProperty("mail.service.url");
        String requestURL = mailServiceUrl + "/api/v1/mail";

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String port = environment.getProperty("server.port");
        String appUrl = "http://" + ip + ":" + port;

        Map<String, Object> body = new HashMap<>();
        body.put("type", "SIGNUP");
        body.put("receiver", user.getEmail());
        body.put("sender", "blusky10@naver.com");
        body.put("subject", "Registration Confirmation");
        body.put("contents", "To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getToken());

        return new RequestObj(requestURL, body);
    }


}
