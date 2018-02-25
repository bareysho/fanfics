package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> findAll();
    String[] findAllAsString();
    Tag findByName(String name);
    Set<Tag> getTegsFromString(String addedTags);
}