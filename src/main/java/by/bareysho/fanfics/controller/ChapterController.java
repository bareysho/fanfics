package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.service.ChapterService;
import by.bareysho.fanfics.service.FanficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes(types = Fanfic.class)
public class ChapterController {

    @Autowired
    private FanficService fanficService;

    @Autowired
    private ChapterService chapterService;

    @RequestMapping(value = "/createChapter/{id}", method = RequestMethod.GET)
    public String createChapter(@PathVariable(value = "id") Long id, Model model) {
        Fanfic fanfic = fanficService.findById(id);
        model.addAttribute("fanficToAddChapter", fanfic);
        model.addAttribute("chapterForm", new Chapter());
        return "addChapter";
    }

    @RequestMapping(value = "/addChapter", method = RequestMethod.POST)
    public String addChapter(@ModelAttribute("fanficToAddChapter") Fanfic fanfic,
                             @ModelAttribute("chapterForm") Chapter chapter,
                             BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            this.setProjectErrorCount(bindingResult.getErrorCount());
//            return "redirect:/projects/new";
//        }
        System.out.println(fanfic.getDescription());
        chapter.setOvnerFanfic(fanfic);
        chapterService.save(chapter);

        return "redirect:/fanfics/" + fanfic.getId() + "/edit";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/chapters/{chapterId}/edit"}, method = RequestMethod.GET)
    public String editChapter(Model model, @PathVariable(value = "fanficid") Long fanficId, @PathVariable(value = "chapterId") Long chapterId) {
        Chapter chapter = chapterService.findById(chapterId);
        model.addAttribute("fanficId", fanficId);
        model.addAttribute("editChapter", chapter);

        return "editChapter";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/chapters/{chapterId}"}, method = RequestMethod.POST)
    public String editChapter(Model model, Chapter chapter, @PathVariable(value = "fanficid") Long fanficId, @PathVariable(value = "chapterId") Long chapterId) {
        Chapter bufChapter = chapterService.findById(chapterId);

        bufChapter.setChapterName(chapter.getChapterName());
        bufChapter.setText(chapter.getText());

        chapterService.save(bufChapter);

        return "redirect:/fanfics/" + fanficId + "/edit";
    }


    @RequestMapping(value = "/fanfics/{fanficid}/chapters/{chapterid}/delete")
    public String deleteChapter(@PathVariable(value = "chapterid") Long chapterid,
                                @PathVariable(value = "fanficid") Long fanficid) {


        chapterService.deleteChaptersById(chapterid);

        return "redirect:/readFanfic/" + fanficid;
    }
}
