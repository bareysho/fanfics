package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.repository.FanficRepository;
import by.bareysho.fanfics.service.FanficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanficServiceImpl implements FanficService {

    @Autowired
    FanficRepository fanficRepository;

    @Override
    public Fanfic findById(Long id) {
        return fanficRepository.findOne(id);
    }

    @Override
    public List<Fanfic> findAll() {
        return fanficRepository.findAll();
    }

    @Override
    public List<Fanfic> findByUserId(Long id) {
        return fanficRepository.findByCreator_Id(id);
    }

    @Override
    public void save(Fanfic fanfic) {
        fanficRepository.save(fanfic);
    }
}
