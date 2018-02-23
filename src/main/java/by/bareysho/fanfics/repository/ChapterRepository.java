package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByOvnerFanfic_Id(Long id);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM chapters WHERE fanfic_id = :f_id", nativeQuery = true)
    public void removeChaptersByOvnerFanfic_Id(@Param("f_id") Long fanfic_id);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM chapters WHERE id = :chap_id", nativeQuery = true)
    public void removeChaptersById(@Param("chap_id") Long chapter_id);
}
