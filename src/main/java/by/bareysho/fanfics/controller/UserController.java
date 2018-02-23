package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.*;
import by.bareysho.fanfics.security.ulogin.ULoginAuthToken;
import by.bareysho.fanfics.security.ulogin.UloginAuthenticationFilter;
import by.bareysho.fanfics.security.ulogin.UloginAuthentificationProvider;
import by.bareysho.fanfics.service.*;
import by.bareysho.fanfics.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FanficService fanficService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private GenreService genreService;

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("userForm", new CustomUser());
//
//        return "registration";
//    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        CustomUser dbCustomUser = securityService.getCurrentUser();

        List<Fanfic> fanfics = fanficService.findByUserId(dbCustomUser.getId());
        System.out.println(fanfics);
        model.addAttribute("currentUser", dbCustomUser);

        model.addAttribute("username", dbCustomUser.getUsername());
        model.addAttribute("email", dbCustomUser.getEmail());
        model.addAttribute("firstname", dbCustomUser.getFirstName());
        model.addAttribute("lastname", dbCustomUser.getLastName());
        model.addAttribute("userFanfics", fanfics);

        return "profile";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String registration(@ModelAttribute("userForm") CustomUser userForm, BindingResult bindingResult, Model model, WebRequest request) {
//        userValidator.validate(userForm, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        userForm.setPassword(userService.encodePassword(userForm.getPassword()));
//        userService.save(userForm);
//
//        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
//
//        try {
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
//                    (userForm, request.getLocale(), request.getContextPath()));
//        } catch (Exception me) {
//            me.printStackTrace();
//            return "emailError";
//        }
//        model.addAttribute("username", userForm.getUsername());
//        return "regSuccess";
//    }


    @RequestMapping(value = {"/", "welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        List<Fanfic> fanfics = fanficService.findAll();
        CustomUser customUser = userService.getLoginUser();

        model.addAttribute("currentUser", customUser);
        model.addAttribute("allFanfics", fanfics);

        return "welcome";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {

        List<CustomUser> userList = userService.findAll();
        model.addAttribute("users", userList);

        return "admin";
    }

//    @RequestMapping(value = "/sendEmailConfirmation", method = RequestMethod.POST)
//    public String emailConfirmation(WebRequest request) {
//
//        CustomUser dbCustomUser = securityService.getCurrentUser();
//
//        try {
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
//                    (dbCustomUser, request.getLocale(), request.getContextPath()));
//        } catch (Exception me) {
//            me.printStackTrace();
//        }
//        return "redirect:";
//    }

    @RequestMapping(value = {"/users/{userid}"}, method = RequestMethod.POST)
    public String updateFanfic(Model model, CustomUser user, @RequestPart(value = "files", required = false) MultipartFile file,
                               @PathVariable(value = "userid") Long id) throws IOException {
        CustomUser userBuf = userService.findById(id);

        userBuf.setFirstName(user.getFirstName());
        userBuf.setLastName(user.getLastName());
        userBuf.setEmail(user.getEmail());

        if (user.getPassword() != null) {
            if (!user.getPassword().equals(userBuf.getPassword())) {
                userBuf.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        }

        if (!file.isEmpty()) {
            userBuf.setProfileImg(imageService.uploadPhoto(file.getBytes()));
        }


        userService.save(userBuf);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/users/delete")
    public String usersDelete(@RequestParam("checkthis") Long[] token){
        System.out.println(token);

        return "redirect:admin";
    }

    @RequestMapping(value = "/ReturnCheckedUsers")
    public String returning(@RequestParam("checkthis") Long[] token, @RequestParam("action") String action) {
        if (action.equals("ban")) {
            for (int i = 0; i < token.length; i++) {
                userService.banUserById(token[i]);
            }
        } else if (action.equals("button.unban")) {
            for (int i = 0, le = token.length; i < le; i++) {
                userService.unbanUserById(token[i]);
            }
        } else if (action.equals("delete")) {
            for (int i = 0; i < token.length; i++) {
                System.out.println(token[i]);
//                fanficService.deleteByCreatorId(token[i]);
                userService.deleteUserById(token[i]);
            }
        }
        return "redirect:admin";
    }
}
