package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
