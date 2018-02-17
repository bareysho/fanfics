package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Fanfic;

import java.util.List;

public interface FanficService {
    Fanfic findById(Long id);
    void save(Fanfic fanfic);
}
