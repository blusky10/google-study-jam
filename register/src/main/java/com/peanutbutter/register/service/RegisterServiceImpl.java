package com.peanutbutter.register.service;

import com.peanutbutter.register.model.User;
import com.peanutbutter.register.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        LOGGER.debug("[ACCOUNT_LOGGER] userExists : " + userExists);

        if (userExists.isPresent()) {
            return null;
        } else {
            user.setEnabled(false);
            return userRepository.save(user);
        }
    }
}
