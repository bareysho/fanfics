package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.model.ChapterRating;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.repository.FanficRepository;
import by.bareysho.fanfics.service.ChapterService;
import by.bareysho.fanfics.service.FanficService;
import by.bareysho.fanfics.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ResourceBundle;

@Service
public class FanficServiceImpl implements FanficService {

    @Autowired
    private FanficRepository fanficRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public Fanfic findById(Long id) {
        return fanficRepository.findOne(id);
    }

    @Override
    public List<Fanfic> findAll() {
        return fanficRepository.findAll();
    }

    @Override
    public List<Fanfic> findByUserId(Long id) {
        return fanficRepository.findByCreator_Id(id);
    }

    @Override
    public void save(Fanfic fanfic) {
        fanficRepository.save(fanfic);
    }

    @Override
    @Transactional
    public void deleteByCreatorId(Long id) {
        fanficRepository.deleteFanficsByCreator_Id(id);
    }

    @Override
    public void deleteByFanficId(Long id) {
        fanficRepository.removeFanficById(id);
    }


    @Override
    public double calculateFanficAverage(Fanfic fanfic) {

        List<Chapter> chapters = chapterService.findByFanficId(fanfic.getId());
        if (chapters.size() == 0) {
            return 0;
        }
        double total = 0;
        int size = 0;
        for (Chapter chapter : chapters) {
            total += chapterService.calculateChapterAverage(chapter.getId());
            if (ratingService.findAllByChapter_Id(chapter.getId()).size() != 0) {
                size++;
            }
        }
        System.out.println(size);
        return total / size;
    }

    @Override
    public List<Fanfic> findTop10OrderByAverageRate() {
        return fanficRepository.findTop10ByOrderByAverageRateDesc();
    }

    @Override
    public List<Fanfic> findTop10ByOrderByCreationDateDesc() {
        return fanficRepository.findTop10ByOrderByCreationDateDesc();
    }

    @Override
    public String generateDeleteResponse() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(generateHtmlFanfics(fanficRepository.findTop10ByOrderByAverageRateDesc(),
                resourceBundle.getString("label.top10byrating")));
        stringBuilder.append(generateHtmlFanfics(fanficRepository.findTop10ByOrderByCreationDateDesc(),
                resourceBundle.getString("label.top10byDate")));


        return stringBuilder.toString();
    }

    private String generateHtmlFanfics(List<Fanfic> fanfics, String title) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"row\">\n" +
                "        <div class=\"col-md-12\">\n" +
                "            <h2>" + title + "</h2>\n" +
                "            <hr>\n");
        for (Fanfic fanfic : fanfics) {
            stringBuilder.append("<div>" +
                    "                   <div style=\"width: 350px;\" class=\"col-sm-6 col-md-4\">\n" +
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
                    "                            <p><b>" + resourceBundle.getString("label.rating") + "</b> " + ": " + fanfic.getAverageRate() + "</p>" +
                    "                            <p><b>" + resourceBundle.getString("title.author") + ": " + "</b><a href=\"/profile/\"" + fanfic.getCreator().getId() + ">" + fanfic.getCreator().getUsername() + "</a>" +
                    "                            <p><b>" + resourceBundle.getString("label.description") + "&#58" + "</b></p>\n" +
                    "                            <script>\n" +
                    "                                   $(function () {\n" +
                    "                                        $('.box').truncateText();\n" +
                    "                                   });\n" +
                    "                            </script>" +
                    "                            <div style=\"height: 70px; padding-bottom: 20px;\" class=\"box\">\n" +
                    "                                <div>\n" +
                    "                                    <p>" + fanfic.getDescription() + "</p>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "\n" +
                    "                            <p><a href=\"/readFanfic/" + fanfic.getId() + "\" " +
                    "                                   class=\"btn btn-primary\">" + resourceBundle.getString("button.read") + "</a>\n" +
                    "                                <a href=\"'/fanfics/'" + fanfic.getId() + "'/edit'\"\n" +
                    "                                   class=\"btn btn-primary btn-sm\">" + resourceBundle.getString("button.edit") + "</a>" +
                    "                                <a href=\"javascript:ajax_delete(" + fanfic.getId() + ")\" class=\"btn btn-primary\">" + resourceBundle.getString("button.delete") + "</a></p>\n" +
                    "                            <p style=\"text-align: right; color: #8b8e8d\">" + fanfic.getDateAsString() + "</p>" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>" +
                    "               </div>");

        }
        stringBuilder.append("</div>\n" +
                "    </div>");
        return stringBuilder.toString();
    }
}
