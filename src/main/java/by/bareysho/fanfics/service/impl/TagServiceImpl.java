package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Tag;
import by.bareysho.fanfics.repository.TagRepository;
import by.bareysho.fanfics.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByTagName(name);
    }

    @Override
    public Set<Tag> getTegsFromString(String addedTags){
        Set<Tag> tags = new HashSet<>();
        if (addedTags ==null || addedTags.length() == 0){
            return tags;
        }
        String[] strTags = addedTags.split(",");
        for (int i = 0; i < strTags.length; i++) {
            Tag tag = tagRepository.findByTagName(strTags[i]);
            if (tag == null) {
                tag = new Tag();
                tag.setTagName(strTags[i]);
            }
            tags.add(tag);
        }
        return  tags;
    }
}
