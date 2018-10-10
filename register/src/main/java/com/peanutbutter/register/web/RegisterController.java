package com.peanutbutter.register.web;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.peanutbutter.register.entity.User;
import com.peanutbutter.register.repository.UserRepository;
import com.peanutbutter.register.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistration(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        User register = registerService.register(user);

        if (register == null){
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
        }else{
            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + register.getEmail());
        }

        modelAndView.setViewName("register");

        LOGGER.debug("Register Info " + register.toString());

        return modelAndView;
    }

    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        Optional<User> userByToken = userRepository.findByToken(token);

        modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");

        userByToken.ifPresent(user -> {
            modelAndView.clear();
            modelAndView.setViewName("confirm");
            modelAndView.addObject("confirmationToken", user.getToken());
        });

        return modelAndView;
    }

    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        String password = requestParams.get("password");
        String token = requestParams.get("token");

        if (isInvalidPassword(password)) {
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
            modelAndView.setViewName("redirect:confirm?token=" + token);
            return modelAndView;
        }

        Optional<User> userByToken = userRepository.findByToken(token);

        registerService.confirm(userByToken.get(), password);

        modelAndView.setViewName("confirm");
        modelAndView.addObject("successMessage", "Your password has been set!");
        return modelAndView;
    }


    private boolean isInvalidPassword(String password){
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(password);

        if (strength.getScore() < 3) {
            return true;
        }

        return false;
    }
}
