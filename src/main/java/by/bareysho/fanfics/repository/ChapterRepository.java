package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
