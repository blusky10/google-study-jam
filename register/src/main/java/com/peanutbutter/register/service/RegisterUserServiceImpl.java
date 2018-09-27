package com.peanutbutter.register.service;

import com.peanutbutter.register.model.User;
import com.peanutbutter.register.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUserServiceImpl implements RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        // Lookup user in database by e-mail
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        LOGGER.debug("[ACCOUNT_LOGGER] userExists : " + userExists);

        if (userExists.isPresent()) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else { // new user so we create user and send confirmation e-mail

            // Disable user until they click on confirmation link in email
            user.setEnabled(false);

            userRepository.save(user);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
            modelAndView.setViewName("register");
        }

    }
}
