package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.*;
import by.bareysho.fanfics.service.*;
import by.bareysho.fanfics.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    private UserValidator userValidator;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        CustomUser dbCustomUser = securityService.getCurrentUser();
        CustomUser userInfo = userService.findById(dbCustomUser.getId());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("editUser", dbCustomUser);
        model.addAttribute("currentUser", dbCustomUser);

        return "profile";
    }

    @RequestMapping(value = "/profile/{userid}")
    public String user(Model model, @PathVariable(value = "userid") Long id){
        CustomUser currentUser = userService.getLoginUser();
        CustomUser userInfo = userService.findById(id);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("editUser", currentUser);
        model.addAttribute("currentUser", currentUser);


        return "profile";
    }

    @RequestMapping(value = {"/", "welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        List<Fanfic> top10byRating = fanficService.findTop10OrderByAverageRate();
        List<Fanfic> top10ByDate = fanficService.findTop10ByOrderByCreationDateDesc();
        List<Fanfic> fanfics = fanficService.findAll();
        CustomUser customUser = userService.getLoginUser();

        model.addAttribute("currentUser", customUser);
        model.addAttribute("top10byRating", top10byRating);
        model.addAttribute("top10byDate", top10ByDate);
        model.addAttribute("allFanfics", fanfics);

        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {

        List<CustomUser> userList = userService.findAll();
        model.addAttribute("users", userList);

        return "admin";
    }

    @RequestMapping(value = {"/users/{userid}"}, method = RequestMethod.POST)
    public String updateFanfic(Model model, @ModelAttribute("currentUser") CustomUser user,
                               @ModelAttribute("editUser") CustomUser editUser,
                               @RequestPart(value = "files", required = false) MultipartFile file,
                               @PathVariable(value = "userid", required = false) Long id,
                               BindingResult bindingResult) throws IOException {

        userValidator.validateEmail(user.getEmail(), bindingResult);

        if (bindingResult.hasErrors()) {
            CustomUser dbCustomUser = userService.findById(id);
            model.addAttribute("currentUser", dbCustomUser);
            model.addAttribute("editUser", editUser);
            return "profile";
        }
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
    public String usersDelete(@RequestParam("checkthis") Long[] token) {
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
                userService.deleteUserById(token[i]);
            }
        } else if (action.equals("setAdmin")){
            for(int i = 0; i < token.length; i++){
                userService.setAdmin(new Long(token[i]));
            }
        }
        return "redirect:admin";
    }
}
