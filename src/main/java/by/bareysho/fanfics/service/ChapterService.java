package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Chapter;

public interface ChapterService {
    Chapter findById(Long id);
    void save(Chapter chapter);
}
