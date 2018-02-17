package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.repository.ChapterRepository;
import by.bareysho.fanfics.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterRepository chapterRepository;

    @Override
    public Chapter findById(Long id) {
        return chapterRepository.findOne(id);
    }

    @Override
    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }
}
