package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.ChapterRating;
import by.bareysho.fanfics.repository.RatingRepository;
import by.bareysho.fanfics.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public ChapterRating findById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<ChapterRating> findAllByUser_Id(Long id) {
        return ratingRepository.findAllByUser_Id(id);
    }

    @Override
    public List<ChapterRating> findAllByChapter_Id(Long id) {
        return ratingRepository.findAllByChapter_Id(id);
    }

    @Override
    public void save(ChapterRating chapterRating) {
        ratingRepository.save(chapterRating);
    }

    @Override
    public ChapterRating findByUserAndChapterId(Long userId, Long chapterId) {
        return ratingRepository.findByUser_IdAndChapter_Id(userId, chapterId);
    }
}
