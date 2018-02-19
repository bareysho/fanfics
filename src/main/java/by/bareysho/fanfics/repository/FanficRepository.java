package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Fanfic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FanficRepository extends JpaRepository<Fanfic, Long> {
    List<Fanfic> findByCreator_Id(Long id);
}
