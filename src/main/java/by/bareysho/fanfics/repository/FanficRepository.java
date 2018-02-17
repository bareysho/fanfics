package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Fanfic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanficRepository extends JpaRepository<Fanfic, Long> {
}
