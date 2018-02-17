package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.service.ChapterService;
import by.bareysho.fanfics.service.FanficService;
import by.bareysho.fanfics.service.ImageService;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@SessionAttributes(types = Fanfic.class)
public class FanficController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Autowired
    FanficService fanficService;

    @Autowired
    ChapterService chapterService;

    @RequestMapping(value = "/createFanfic", method = RequestMethod.GET)
    public String createFanfic(Model model) {
        model.addAttribute("fanficForm", new Fanfic());
        return "addFanfic";
    }

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
        System.out.println(fanfic.getFanficName());
        System.out.println(fanfic.getDescription());
        fanfic.setCreator(user);

        fanficService.save(fanfic);

        System.out.println(fanfic.getId());

        return "redirect:/createChapter/" + fanfic.getId();
    }

    @RequestMapping(value = "/createChapter/{id}", method = RequestMethod.GET)
    public String createChapter(@PathVariable(value = "id") Long id, Model model) {
        Chapter chapter = new Chapter();
        Fanfic fanfic = fanficService.findById(id);
        model.addAttribute(fanfic);
        model.addAttribute("chapterForm", chapter);
        return "addChapter";
    }

    @RequestMapping(value = "/addChapter", method = RequestMethod.POST)
    public String addChapter(@ModelAttribute("fanfic") Fanfic fanfic, @ModelAttribute("chapterForm") Chapter chapter, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            this.setProjectErrorCount(bindingResult.getErrorCount());
//            return "redirect:/projects/new";
//        }
        System.out.println(fanfic.getId());
        System.out.println(fanfic.getFanficName());
        chapter.setOvnerFanfic(fanfic);

        chapterService.save(chapter);
        return "redirect:/createFanfic";
    }

}
