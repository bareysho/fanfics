package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.*;
import by.bareysho.fanfics.repository.search.FulltextRepository;
import by.bareysho.fanfics.service.*;
import by.bareysho.fanfics.validator.FanficValidator;
import by.bareysho.fanfics.validator.UserValidator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Controller
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

    @Autowired
    private TagService tagService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FulltextRepository fulltextRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private FanficValidator fanficValidator;

    @RequestMapping(value = "/fanfics/create", method = RequestMethod.GET)
    public String createFanfic(Model model) {
        model.addAttribute("fanficForm", new Fanfic());
        model.addAttribute("allTags", tagService.findAllAsString());
        model.addAttribute("allGenres", genreService.findAll());

        return "addNewFanfic";
    }

    @RequestMapping(value = {"/addFanfic"}, method = RequestMethod.GET)
    public String changeLocale(Model model, @ModelAttribute("fanficForm") Fanfic fanfic) {
        model.addAttribute("allTags", tagService.findAllAsString());
        model.addAttribute("allGenres", genreService.findAll());

        return "addNewFanfic";
    }

    @RequestMapping(value = {"/addFanfic"}, method = RequestMethod.POST)
    public String addProject(Model model, @ModelAttribute("fanficForm") Fanfic fanfic,
                             @RequestPart("files") MultipartFile file,
                             @RequestParam("stringtags") String addedTags,
                             @RequestParam(value = "selectedGenres", required = false) String selectedGenres,
                             BindingResult bindingResult) throws Exception {

        fanficValidator.validate(fanfic, bindingResult);
        fanficValidator.fanficGenresValidate(selectedGenres, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allTags", tagService.findAllAsString());
            model.addAttribute("allGenres", genreService.findAll());
            return "addNewFanfic";
        }

        Set<Tag> tags = tagService.getTegsFromString(addedTags);
        Set<Genre> genres = genreService.getGenresFromString(selectedGenres);
        CustomUser user = userService.getLoginUser();

        java.sql.Date date = new Date(System.currentTimeMillis());
        fanfic.setCreationDate(date);
        fanfic.setGenres(genres);
        fanfic.setImage(imageService.uploadPhoto(file.getBytes()));
        fanfic.setCreator(user);
        fanfic.setTags(tags);

        fanficService.save(fanfic);

        return "redirect:/fanfics/" + fanfic.getId() + "/read";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}"}, method = RequestMethod.POST)
    public String updateFanfic(Model model, Fanfic fanfic, @RequestPart(value = "files", required = false) MultipartFile file,
                               @RequestParam("stringtags") String addedTags,
                               @RequestParam(value = "selectedGenres", required = false) String selectedGenres,
                               @PathVariable(value = "fanficid", required = false) Long id,
                               BindingResult bindingResult) throws IOException {
        fanficValidator.validate(fanfic, bindingResult);
        fanficValidator.fanficGenresValidate(selectedGenres, bindingResult);

        if (bindingResult.hasErrors()) {
            Fanfic editFanfic = fanficService.findById(id);
            model.addAttribute("currentFanfic", editFanfic);
            model.addAttribute("allTags", tagService.findAllAsString());
            model.addAttribute("allGenres", genreService.findAll());

            return "editFanfic";
        }
        Set<Genre> genres = genreService.getGenresFromString(selectedGenres);
        Set<Tag> tags = tagService.getTegsFromString(addedTags);
        Fanfic fanficBuf = fanficService.findById(id);

        fanficBuf.setFanficName(fanfic.getFanficName());

        fanficBuf.setImage(imageService.uploadPhoto(file.getBytes()));
        fanficBuf.setGenres(genres);
        fanficBuf.setDescription(fanfic.getDescription());
        fanficBuf.setTags(tags);

        fanficService.save(fanficBuf);

        return "redirect:/fanfics/" + id + "/read";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/edit"}, method = RequestMethod.GET)
    public String editFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
        Fanfic fanficToEdit = fanficService.findById(id);
        if(!userService.getLoginUser().hasRole("ADMIN") && !fanficToEdit.getCreator().getId().equals(userService.getLoginUser().getId())){
            return "redirect:/profile";
        }
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allTags", tagService.findAllAsString());
        model.addAttribute("currentFanfic", fanficToEdit);

        return "editFanfic";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/page_edit/delete"})
    public String pageEditDeleteFanfic(@PathVariable(value = "fanficid") Long id) {
        Fanfic fanficToEdit = fanficService.findById(id);
        if(!userService.getLoginUser().hasRole("ADMIN") && !fanficToEdit.getCreator().getId().equals(userService.getLoginUser().getId())){
            return "redirect:/profile";
        }
        fanficService.deleteByFanficId(id);
        return "redirect:/profile";
    }

    @ResponseBody
    @PostMapping(value = {"/fanfics/{fanficid}/page_profile/delete"})
    public String pageProfileDeleteFanfic(@PathVariable(value = "fanficid") Long id) {
        Fanfic fanficToEdit = fanficService.findById(id);
        if(!userService.getLoginUser().hasRole("ADMIN") && !fanficToEdit.getCreator().getId().equals(userService.getLoginUser().getId())){
            return "redirect:/profile";
        }
        fanficService.deleteByFanficId(id);
        CustomUser customUser = userService.getLoginUser();
        StringBuilder stringBuilder = new StringBuilder();
        for (Fanfic fanfic : fanficService.findByUserId(customUser.getId())) {
            stringBuilder.append("<tr>\n" +
                    "   <td>" + fanfic.getId() + "</td>\n" +
                    "   <td><a href=\"/readFanfic/" + fanfic.getId() + "\">" + fanfic.getFanficName() + "</a></td>\n" +
                    "   <td>" + fanfic.getChapters().size() + "</td>\n" +
                    "   <td></td>\n" +
                    "   <td style=\"\">\n" +
                    "       <a rel=\"tooltip\" title=\"Edit\" class=\"table-action edit\" " +
                    "               href=\"/fanfics/" + fanfic.getId() + "/edit\">\n" +
                    "           <i class=\"fa fa-edit\"></i></a>\n" +
                    "       <a rel=\"tooltip\" title=\"Remove\" class=\"table-action remove\" " +
                    "               href=\"javascript:ajax_delete(\" + fanfic.getId() +\")\">\n" +
                    "           <i class=\"fa fa-remove\"></i></a>\n" +
                    "   </td>\n" +
                    "</tr>\n");
        }
        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("<tr class=\"no-records-found\"><td colspan=\"4\">No matching records found</td></tr>");
        }
        return stringBuilder.toString();
    }

    @ResponseBody
    @PostMapping(value = {"/fanfics/{fanficid}/page_welcome/delete"})
    public String pageWelcomeDeleteFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
        Fanfic fanficToEdit = fanficService.findById(id);
        if(!userService.getLoginUser().hasRole("ADMIN") && !fanficToEdit.getCreator().getId().equals(userService.getLoginUser().getId())){
            return "redirect:/profile";
        }
        fanficService.deleteByFanficId(id);
        return fanficService.generateDeleteResponse();
    }

    @RequestMapping(value = "/fanfics/{id}/read", method = RequestMethod.GET)
    public String readFanfic(@PathVariable(value = "id") Long id, Model model) {
        Fanfic fanfic = fanficService.findById(id);
        List<Chapter> chapters = chapterService.findByFanficId(id);
        Genre genre = genreService.findById(fanfic.getGenres().iterator().next().getId());
        CustomUser customUser = userService.getLoginUser();

        model.addAttribute("currentUser", customUser);
        model.addAttribute("currentFanfic", fanfic);
        model.addAttribute("genre", genre);
        model.addAttribute("fanficChapters", chapters);
        model.addAttribute("comment", new Comment());
        model.addAttribute("allTags", tagService.findAllAsString());

        return "fanfic";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/comment/"}, method = RequestMethod.POST)
    public String saveComment(Model model, @PathVariable(value = "fanficid") Long id,
                              @Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/fanfics/{fanficid}/read";
        }
        CustomUser user = userService.getLoginUser();
        Fanfic fanfic = fanficService.findById(id);
        comment.setUserCreator(user.getFullName());
        comment.setOvnerFanfic(fanfic);
        commentService.save(comment);
        return "redirect:/fanfics/" + id + "/read";
    }

    @ResponseBody
    @PostMapping(value = {"/comments/{commentid}/like"})
    public String like(@PathVariable(value = "commentid") int id) {
        CustomUser customUser = userService.getLoginUser();
        Comment comment = commentService.findById(Long.valueOf(id));
        StringBuilder stringBuilder = new StringBuilder();

        Set<Comment> likedComments = customUser.getLikedComments();
        if (likedComments.contains(comment)) {
            likedComments.remove(comment);
            stringBuilder.append("<a onclick=\"ajax_like(" + comment.getId() + ")\" " +
                    "class=\"like\"><i class=\"far fa-heart\"></i></a>");
        } else if (!likedComments.contains(comment)) {
            likedComments.add(comment);
            stringBuilder.append("<a onclick=\"ajax_like(" + comment.getId() + ")\" " +
                    "class=\"like\"><i class=\"fas fa-heart\"></i></a>");
        } else {
            stringBuilder.append("<i class=\"far fa-heart\"></i></a>");
        }

        customUser.setLikedComments(likedComments);
        userService.save(customUser);

        stringBuilder.append("<span class=\"likes\">" + comment.getUserWhoLiked().size() + "</span>");

        return stringBuilder.toString();
    }

    @RequestMapping(value = "/fanfics/search")
    public String fullTextSearch(Model model, String q) {
        if (q.length() == 0){
            return "redirect:/welcome";
        }
        model.addAttribute("currentUser", userService.getLoginUser());
        model.addAttribute("fanficsList", fulltextRepository.search(q));
        return "search";
    }

    @ResponseBody
    @PostMapping(value = "/rating")
    public String rating(@RequestParam("vote-id") Long chapterId, @RequestParam("score") int score) throws InterruptedException {
        Chapter chapter = chapterService.findById(chapterId);
        CustomUser customUser = userService.getLoginUser();
        ChapterRating chapterRating = new ChapterRating();

        if (chapter.checkReated(customUser)) {
            chapterRating = ratingService.findByUserAndChapterId(customUser.getId(), chapterId);
            chapterRating.setAmount(score);
        } else {
            chapterRating.setUser(customUser);
            chapterRating.setChapter(chapter);
            chapterRating.setAmount(score);
        }

        ratingService.save(chapterRating);

        Thread.sleep(400);
        Fanfic fanfic = fanficService.findById(chapter.getOvnerFanfic().getId());
        fanfic.setAverageRate(fanficService.calculateFanficAverage(fanfic));
        fanficService.save(fanfic);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Спасибо. Ваш голос учтен");
        jsonObject.put("status", "OK");

        String jsonString = jsonObject.toJSONString();

        return jsonString;
    }

}
