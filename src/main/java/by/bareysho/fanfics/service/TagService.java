package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    String[] findAllAsString();
}