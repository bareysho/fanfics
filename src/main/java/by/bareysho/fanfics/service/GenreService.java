package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
    Genre findById(Long id);
}
