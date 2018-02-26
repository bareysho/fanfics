package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.model.ChapterRating;
import by.bareysho.fanfics.repository.ChapterRepository;
import by.bareysho.fanfics.service.ChapterService;
import by.bareysho.fanfics.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    private RatingService ratingService;

    @Override
    public Chapter findById(Long id) {
        return chapterRepository.findOne(id);
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public List<Chapter> findByFanficId(Long id) {
        return chapterRepository.findByOvnerFanfic_Id(id);
    }

    @Override
    public void deleteChaptersByFanficId(Long id) {
        chapterRepository.removeChaptersByOvnerFanfic_Id(id);
    }
    @Override
    public void deleteChaptersById(Long id) {
        chapterRepository.removeChaptersById(id);
    }

    @Override
    public double calculateChapterAverage(Long chapterId) {
        double all = 0;
        List<ChapterRating> ratings = ratingService.findAllByChapter_Id(chapterId);
        if (ratings.size() == 0) {
            System.out.println("ne vidit");
            return 0;
        }
        for (ChapterRating chapterRating : ratings) {
            all += chapterRating.getAmount();
        }
        System.out.println(all);
        System.out.println(ratings.size());
        System.out.println("chapter average " + all / ratings.size());

        return all / ratings.size();
    }
}
