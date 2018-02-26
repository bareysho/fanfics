package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.service.UserService;
import by.bareysho.fanfics.service.impl.EmailServiceImpl;
import by.bareysho.fanfics.validator.UserValidator;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, CustomUser user) {
        modelAndView.addObject("customUser", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid CustomUser user,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {
        CustomUser usernameExists = userService.findByUsername(user.getUsername());
        CustomUser userEmailExists = userService.findByEmail(user.getEmail());

        if (userEmailExists != null) {
            System.out.println("email");
            setEmailInvalid(modelAndView, bindingResult);
        }
        if(usernameExists != null){
            System.out.println("username");
            setUsernameInvalid(modelAndView, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        }
        else {
            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.save(user);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                    + appUrl + ":8080/confirm?token=" + user.getConfirmationToken());
            registrationEmail.setFrom("noreply@domain.com");
            emailServiceImpl.sendEmail(registrationEmail);
            modelAndView.addObject("confirmationMessage",
                    "A confirmation e-mail has been sent to " + user.getEmail());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {
        CustomUser user = userService.findByConfirmationToken(token);
        if (user == null) {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else {
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult,
                                            @RequestParam Map<String, String> requestParams,
                                            RedirectAttributes redir) {
        modelAndView.setViewName("confirm");
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(requestParams.get("password"));
        if (strength.getScore() < 3) {
            modelAndView.addObject("errorMessage",
                    "Your password is too weak.  Choose a stronger one.");
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            return modelAndView;
        }
        CustomUser user = userService.findByConfirmationToken(requestParams.get("token"));
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        user.setEnabled(true);
        userService.save(user);
        modelAndView.addObject("successMessage", "Your password has been set!");
        return modelAndView;
    }

    private void setEmailInvalid(ModelAndView modelAndView, BindingResult bindingResult){
        modelAndView.addObject("alreadyRegisteredMessage",
                "Email registred");
        modelAndView.setViewName("register");
        bindingResult.reject("email");
    }
    private void setUsernameInvalid(ModelAndView modelAndView, BindingResult bindingResult){
        modelAndView.addObject("alreadyRegisteredMessage",
                "Username registred");
        modelAndView.setViewName("register");
        bindingResult.reject("username");
    }

    private void setUsernameAndEmailInvalid(ModelAndView modelAndView, BindingResult bindingResult){
        modelAndView.addObject("alreadyRegisteredMessage",
                "Email and username registred");
        modelAndView.setViewName("register");
        bindingResult.reject("emailusername");
    }

}