package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Fanfic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FanficRepository extends JpaRepository<Fanfic, Long> {
    List<Fanfic> findByCreator_Id(Long id);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM fanfics WHERE creator_id = :c_id", nativeQuery = true)
    public void deleteFanficsByCreator_Id(@Param("c_id") Long creator_id);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM fanfics WHERE id = :id", nativeQuery = true)
    public void removeFanficById(@Param("id") Long id);
}
