package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.model.Comment;
import by.bareysho.fanfics.service.*;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Fanfic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes(types = Fanfic.class)
public class FanficController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FanficService fanficService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CommentService commentService;

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


    @RequestMapping(value = "/readFanfic/{id}", method = RequestMethod.GET)
    public String readFanfic(@PathVariable(value = "id") Long id, Model model){
        Fanfic fanfic = fanficService.findById(id);
        List<Chapter> chapters = chapterService.findByFanficId(id);

        model.addAttribute("currentFanfic", fanfic);
        model.addAttribute("fanficChapters", chapters);

        return "fanfic";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/comment/"}, method = RequestMethod.POST)
    public String saveComment(Model model, @PathVariable(value = "fanficid") Long id,
                              @Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/readFanfic/{fanficid}";
        }
        CustomUser user = userService.getLoginUser();
        Fanfic fanfic = fanficService.findById(id);
        comment.setUserCreator(user.getFullName());
        comment.setOvnerFanfic(fanfic);
        commentService.save(comment);
        return "redirect:/readFanfic/" + id;
    }

}
