package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Chapter;
import by.bareysho.fanfics.repository.ChapterRepository;
import by.bareysho.fanfics.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterRepository chapterRepository;

    @Override
    public Chapter findById(Long id) {
        return chapterRepository.findOne(id);
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public List<Chapter> findByFanficId(Long id) {
        return chapterRepository.findByOvnerFanfic_Id(id);
    }

    @Override
    public void deleteChaptersByFanficId(Long id) {
        chapterRepository.removeChaptersByOvnerFanfic_Id(id);
    }
    @Override
    public void deleteChaptersById(Long id) {
        chapterRepository.removeChaptersById(id);
    }
}
