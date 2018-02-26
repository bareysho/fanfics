package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Genre;
import by.bareysho.fanfics.repository.GenreRepository;
import by.bareysho.fanfics.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<Genre> getGenresFromString(String selectedGenres){
        Set<Genre> genres = new HashSet<>();
        if (selectedGenres == null){
            return genres;
        }
        System.out.println("|" + selectedGenres + "|");
        String[] selectedGenresIds = selectedGenres.split(",");
        System.out.println(selectedGenresIds.length);

        for (String strId : selectedGenresIds) {
            System.out.println("|"+ strId+"|");
            Long genreId = Long.parseLong(strId);
            Genre genre = genreRepository.findOne(genreId);
            genres.add(genre);
        }
        System.out.println(genres);
        System.out.println("exit");
        return genres;
    }
}
