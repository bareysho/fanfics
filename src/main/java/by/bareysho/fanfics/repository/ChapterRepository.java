package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByOvnerFanfic_Id(Long id);
}
