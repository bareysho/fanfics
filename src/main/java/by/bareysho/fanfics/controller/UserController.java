package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.mail.OnRegistrationCompleteEvent;
import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.service.EmailTokenService;
import by.bareysho.fanfics.service.SecurityService;
import by.bareysho.fanfics.service.UserService;
import by.bareysho.fanfics.validator.UserValidator;
import by.bareysho.fanfics.model.Fanfic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailTokenService emailTokenService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private
    ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new CustomUser());

        return "registration";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        CustomUser dbCustomUser = securityService.getCurrentUser();
        model.addAttribute("username", dbCustomUser.getUsername());
        model.addAttribute("email", dbCustomUser.getEmail());
        return "profile";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") CustomUser userForm, BindingResult bindingResult, Model model, WebRequest request) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userForm.setPassword(userService.encodePassword(userForm.getPassword()));
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (userForm, request.getLocale(), request.getContextPath()));
        } catch (Exception me) {
            me.printStackTrace();
            return "emailError";
        }
        model.addAttribute("username", userForm.getUsername());
        return "regSuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("userForm") CustomUser userForm, Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }


    @RequestMapping(value = {"/", "welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        CustomUser dbCustomUser = securityService.getCurrentUser();
        if (dbCustomUser == null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            userService.save((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return "welcome";
        }

        if (!dbCustomUser.isEnabled()) {
            return "regSuccess";
        }

        return "welcome";
    }


    @RequestMapping(value = "/confSuccess", method = RequestMethod.GET)
    public String confSuccess(Model model, @RequestParam("token") String token) {
        if (token == null)
            return "regSuccess";

        CustomUser dbCustomUser = securityService.getCurrentUser();

        if (emailTokenService.validateToken(token, dbCustomUser)) {
            dbCustomUser.setEnabled(true);
            userService.save(dbCustomUser);
        }

        model.addAttribute("username", dbCustomUser.getUsername());
        return "welcome";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {

        List<CustomUser> userList = userService.findAll();
        model.addAttribute("users", userList);

        return "admin";
    }

    @RequestMapping(value = "/sendEmailConfirmation", method = RequestMethod.POST)
    public String emailConfirmation(WebRequest request) {

        CustomUser dbCustomUser = securityService.getCurrentUser();

        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (dbCustomUser, request.getLocale(), request.getContextPath()));
        } catch (Exception me) {
            me.printStackTrace();
        }
        return "redirect:";
    }

    @RequestMapping(value = "/ReturnCheckedUsers")
    public String returning(@RequestParam("checkthis") Long[] token, @RequestParam("action") String action) {
        if (action.equals("ban")) {
            for (int i = 0; i < token.length; i++) {
                userService.banUserById(token[i]);
            }
        } else if (action.equals("unban")) {
            for (int i = 0, le = token.length; i < le; i++) {
                userService.unbanUserById(token[i]);
            }
        } else if (action.equals("delete")) {
            for (int i = 0; i < token.length; i++) {
                userService.deleteUserById(token[i]);
            }
        }
        return "redirect:admin";
    }
}
