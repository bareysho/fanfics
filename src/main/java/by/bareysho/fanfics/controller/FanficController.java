package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.service.ImageService;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FanficController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = {"/addFanfic"}, method = RequestMethod.POST)
    public String addProject(Fanfic fanfic, @RequestPart("files") MultipartFile file,
                             BindingResult bindingResult, Model model) throws Exception {
//        if (bindingResult.hasErrors()) {
//            this.setProjectErrorCount(bindingResult.getErrorCount());
//            return "redirect:/projects/new";
//        }
        CustomUser user = userService.getLoginUser();
        System.out.println(user.getUsername());
        fanfic.setImage(imageService.uploadPhoto(file.getBytes()));
        System.out.println(fanfic.getImage());
        System.out.println(fanfic.getName());
        System.out.println(fanfic.getDescription());
        return "redirect:/create";
    }
}
