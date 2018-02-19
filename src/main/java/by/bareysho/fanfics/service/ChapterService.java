package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter findById(Long id);
    List<Chapter> findAll();
    void save(Chapter chapter);
    List<Chapter> findByFanficId(Long id);
}
