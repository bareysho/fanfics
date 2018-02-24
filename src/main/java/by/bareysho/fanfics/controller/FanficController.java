package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.model.*;
import by.bareysho.fanfics.repository.search.FulltextRepository;
import by.bareysho.fanfics.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private TagService tagService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FulltextRepository fulltextRepository;

    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/createFanfic", method = RequestMethod.GET)
    public String createFanfic(Model model) {
        model.addAttribute("fanficForm", new Fanfic());
        model.addAttribute("allTags", tagService.findAllAsString());
        model.addAttribute("allGenres", genreService.findAll());
        return "addFanfic";
    }

    @RequestMapping(value = {"/addFanfic"}, method = RequestMethod.POST)
    public String addProject(@ModelAttribute("fanficForm") Fanfic fanfic, @RequestPart("files") MultipartFile file,
                             @RequestParam("stringtags") String addedTags,
                             @RequestParam("selectedGenres") String selectedGenres,
                             BindingResult bindingResult, Model model) throws Exception {

        Set<Tag> tags = new HashSet<>();
        String[] strTags = addedTags.split(",");
        for (int i = 0; i < strTags.length; i++) {
            Tag tag = tagService.findByName(strTags[i]);
            if (tag == null) {
                tag = new Tag();
                tag.setTagName(strTags[i]);
            }
            tags.add(tag);
        }

        CustomUser user = userService.getLoginUser();

        String[] selectedGenresIds = selectedGenres.split(",");
        Set<Genre> genres = new HashSet<>();
        for (String strId : selectedGenresIds) {
            Long genreId = Long.parseLong(strId);
            Genre genre = genreService.findById(genreId);
            genres.add(genre);
        }


        fanfic.setGenres(genres);
        fanfic.setImage(imageService.uploadPhoto(file.getBytes()));
        fanfic.setCreator(user);
        fanfic.setTags(tags);

        fanficService.save(fanfic);

        return "redirect:/createChapter/" + fanfic.getId();
    }

    @RequestMapping(value = "/createChapter/{id}", method = RequestMethod.GET)
    public String createChapter(@PathVariable(value = "id") Long id, Model model) {
        Chapter chapter = new Chapter();
        Fanfic fanfic = fanficService.findById(id);
        model.addAttribute("fanficToAddChapter", fanfic);
        model.addAttribute("chapterForm", chapter);
        return "addChapter";
    }

    @RequestMapping(value = "/addChapter", method = RequestMethod.POST)
    public String addChapter(@ModelAttribute("fanficToAddChapter") Fanfic fanfic, @ModelAttribute("chapterForm") Chapter chapter, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            this.setProjectErrorCount(bindingResult.getErrorCount());
//            return "redirect:/projects/new";
//        }
        chapter.setOvnerFanfic(fanfic);

        chapterService.save(chapter);
        return "redirect:/fanfics/" + fanfic.getId() + "/edit";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}"}, method = RequestMethod.POST)
    public String updateFanfic(Model model, Fanfic fanfic, @RequestPart(value = "files", required = false) MultipartFile file,
                               @RequestParam("stringtags") String addedTags,
                               @RequestParam("selectedGenres") String selectedGenres,
                               @PathVariable(value = "fanficid") Long id) throws IOException {
        System.out.println(selectedGenres);

        String[] selectedGenresIds = selectedGenres.split(",");

        Set<Genre> genres = new HashSet<>();
        for (String strId : selectedGenresIds) {
            Long genreId = Long.parseLong(strId);
            Genre genre = genreService.findById(genreId);
            genres.add(genre);
        }

        Fanfic fanficBuf = fanficService.findById(id);

        fanficBuf.setFanficName(fanfic.getFanficName());

        if (!file.isEmpty()) {
            fanficBuf.setImage(imageService.uploadPhoto(file.getBytes()));
        }

        fanficBuf.setGenres(genres);
        fanficBuf.setDescription(fanfic.getDescription());

        Set<Tag> tags = new HashSet<>();
        String[] strTags = addedTags.split(",");
        for (int i = 0; i < strTags.length; i++) {
            Tag tag = tagService.findByName(strTags[i]);
            if (tag == null) {
                tag = new Tag();
                tag.setTagName(strTags[i]);
            }
            tags.add(tag);
        }

        fanficBuf.setTags(tags);

        fanficService.save(fanficBuf);
        return "redirect:/readFanfic/" + id;
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/edit"}, method = RequestMethod.GET)
    public String editFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
        Fanfic fanficToEdit = fanficService.findById(id);
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allTags", tagService.findAllAsString());
        model.addAttribute("currentFanfic", fanficToEdit);

        return "editFanfic";
    }

    @RequestMapping(value = {"/fanfics/{fanficid}/page_edit/delete"})
    public String pageEditDeleteFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
        fanficService.deleteByFanficId(id);

        return "redirect:/profile";
    }


    @ResponseBody
    @PostMapping(value = {"/fanfics/{fanficid}/page_profile/delete"})
    public String pageProfileDeleteFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
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
        } else if(!likedComments.contains(comment)){
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

    @ResponseBody
    @PostMapping(value = {"/fanfics/{fanficid}/page_welcome/delete"})
    public String pageWelcomeDeleteFanfic(Model model, @PathVariable(value = "fanficid") Long id) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

        fanficService.deleteByFanficId(id);
        StringBuilder stringBuilder = new StringBuilder();
        for (Fanfic fanfic : fanficService.findAll()) {
            stringBuilder.append("<div style=\"width: 350px;\" class=\"col-sm-6 col-md-4\">\n" +
                    "                    <div class=\"thumbnail\">\n" +
                    "                        <div style=\"height: 250px;background-color: rgba(0,0,0,0.11); overflow: hidden;position: relative\">\n" +
                    "                            <img style=\"width: 100%;  margin: auto; position: absolute; top: 0; left: 0; bottom: 0; right: 0;\"\n" +
                    "                                 src=\"" + fanfic.getImage() + "\" alt=\"fanfic image\"/>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "                        <div class=\"caption\">\n" +
                    "                            <h4>" + fanfic.getFanficName() + "</h4>\n" +
                    "                            <p><b>" + resourceBundle.getString("label.genre") + "&#58" + "</b><span>" + fanfic.getGenresAsSting() + "</span>\n" +
                    "                            </p>\n" +
                    "                            <p><b>" + resourceBundle.getString("label.description") + "&#58" + "</b></p>\n" +
                    "                            <div style=\"height: 70px; padding-bottom: 20px;\" class=\"box\">\n" +
                    "                                <div>\n" +
                    "                                    <p>" + fanfic.getDescription() + "</p>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                            <script>\n" +
                    "                                $(function () {\n" +
                    "                                    $('.box').truncateText();\n" +
                    "                                });\n" +
                    "                            </script>\n" +
                    "\n" +
                    "                            <p><a href=\"/readFanfic/" + fanfic.getId() + "\" " +
                    "                                   class=\"btn btn-primary\">" + resourceBundle.getString("button.read") + "</a>\n" +
                    "                                <a href=\"javascript:ajax_delete(" + fanfic.getId() + ")\" class=\"btn btn-primary\">" + resourceBundle.getString("button.delete") + "</a></p>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>");
        }
        return stringBuilder.toString();
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


    @RequestMapping(value = "/readFanfic/{id}", method = RequestMethod.GET)
    public String readFanfic(@PathVariable(value = "id") Long id, Model model) {
        Fanfic fanfic = fanficService.findById(id);
        List<Chapter> chapters = chapterService.findByFanficId(id);
        Genre genre = genreService.findById(fanfic.getGenres().iterator().next().getId());
        CustomUser customUser = userService.getLoginUser();

        model.addAttribute("currentUser", customUser);
        model.addAttribute("currentFanfic", fanfic);
        model.addAttribute("genre", genre);
        model.addAttribute("fanficChapters", chapters);
        model.addAttribute("allTags", tagService.findAllAsString());

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


    @RequestMapping(value = "/fanfics/{fanficid}/chapters/{chapterid}/delete")
    public String deleteChapter(@PathVariable(value = "chapterid") Long chapterid,
                                @PathVariable(value = "fanficid") Long fanficid) {


        chapterService.deleteChaptersById(chapterid);

        return "redirect:/readFanfic/" + fanficid;
    }

    @RequestMapping(value = "/fanfics/search")
    public String fullTextSearch(Model model, String q) {
        model.addAttribute("fanficsList", fulltextRepository.search(q));
        return "search";
    }

    @ResponseBody
    @PostMapping(value = "/rating")
    public String rating(@RequestParam("vote-id") Long chapterId, @RequestParam("score") int score){

        System.out.println(chapterId);
        System.out.println(score);

        Chapter chapter = chapterService.findById(chapterId);
        CustomUser customUser = userService.getLoginUser();

        ChapterRating chapterRating = new ChapterRating();

        if (chapter.checkReated(customUser)){
            chapterRating = ratingService.findByUserAndChapterId(customUser.getId(), chapterId);
            chapterRating.setAmount(score);
        } else {
            chapterRating.setUser(customUser);
            chapterRating.setChapter(chapter);
            chapterRating.setAmount(score);
        }

        ratingService.save(chapterRating);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "\u0421\u043f\u0430\u0441\u0438\u0431\u043e. \u0412\u0430\u0448 \u0433\u043e\u043b\u043e\u0441 \u0443\u0447\u0442\u0435\u043d");
        jsonObject.put("status", "OK");

        String jsonString = jsonObject.toJSONString();

        return jsonString;
    }

}
