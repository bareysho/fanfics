package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.ChapterRating;

import java.util.List;

public interface RatingService {
    ChapterRating findById(Long id);
    List<ChapterRating> findAllByUser_Id(Long id);
    List<ChapterRating> findAllByChapter_Id(Long id);
    void save(ChapterRating chapterRating);
    ChapterRating findByUserAndChapterId(Long userId, Long chapterId);
}
