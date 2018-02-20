package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Tag;
import by.bareysho.fanfics.repository.TagRepository;
import by.bareysho.fanfics.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        List<Tag> tagList = new ArrayList<>();
        for (Tag tag : tagRepository.findAll()){
            if(!tagList.contains(tag)){
                tagList.add(tag);
            }
        }
        return tagList;
    }

    @Override
    public String[] findAllAsString(){
        List<String> stringList = new ArrayList<>();
        for (Tag tag : tagRepository.findAll()){
            if(!stringList.contains(tag)){
                stringList.add(tag.getTagName());
            }
        }

        String[] stringArray = stringList.toArray(new String[0]);

        return stringArray;
    }
}
