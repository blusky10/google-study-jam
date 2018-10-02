package com.peanutbutter.register.service;

import com.peanutbutter.register.dto.RequestObj;
import com.peanutbutter.register.dto.ResponseObj;
import com.peanutbutter.register.entity.User;
import com.peanutbutter.register.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestService restService;

    @Override
    public User register(User user) {

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        LOGGER.debug("[ACCOUNT_LOGGER] userExists : " + userExists);

        if (userExists.isPresent()) {
            return null;
        }

        user.setEnabled(false);

        User registerUser = userRepository.save(user);


        RequestObj trySendMail = trySendMail(registerUser);

        List<ResponseObj> responseObjs = restService.doTry(Arrays.asList(trySendMail));

        restService.confirmAll(responseObjs);

        return registerUser;
    }


    private RequestObj trySendMail(User user){
        final String requestURL = "http://localhost:8081/api/v1/mail";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "SIGNUP");
        requestBody.put("receiver", user.getEmail());
        requestBody.put("sender", "blusky10@naver.com");

        return new RequestObj(requestURL, requestBody);
    }
}
