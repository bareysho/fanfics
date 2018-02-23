package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Genre;
import by.bareysho.fanfics.repository.GenreRepository;
import by.bareysho.fanfics.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findOne(id);
    }
}
