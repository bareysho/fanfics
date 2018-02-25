package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    List<Genre> findAll();
    Genre findById(Long id);
    Set<Genre> getGenresFromString(String selectedGenres);
}
