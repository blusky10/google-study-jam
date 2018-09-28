package com.peanutbutter.register.web;

import com.peanutbutter.register.model.ResponseObj;
import com.peanutbutter.register.model.User;
import com.peanutbutter.register.service.RegisterService;
import com.peanutbutter.register.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RestService restService;

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        User register = registerService.register(user);

        if (register == null){
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
        }else{
            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + register.getEmail());
        }

        modelAndView.setViewName("register");

        ResponseObj responseObj = sendTry(register);

        restService.confirmAll(responseObj.getUri());

        return modelAndView;
    }

    private ResponseObj sendTry(User user){
        final String requestURL = "http://localhost:8081/api/v1/mail";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "SIGNUP");
        requestBody.put("email", user.getEmail());

        return restService.doTry(requestURL, requestBody);
    }

//    private ResponseObj sendConfirm(){
//
//    }
}
