package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Fanfic;

import java.util.List;

public interface FanficService {
    Fanfic findById(Long id);
    List<Fanfic> findAll();
    List<Fanfic> findByUserId(Long id);
    void save(Fanfic fanfic);
}
