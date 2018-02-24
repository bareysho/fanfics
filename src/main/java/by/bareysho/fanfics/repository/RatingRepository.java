package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.ChapterRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<ChapterRating, Long> {
    ChapterRating findById(Long id);
    List<ChapterRating> findAllByUser_Id(Long id);
    List<ChapterRating> findAllByChapter_Id(Long id);
    ChapterRating findByUser_IdAndChapter_Id(Long userId, Long ChapterId);
}
