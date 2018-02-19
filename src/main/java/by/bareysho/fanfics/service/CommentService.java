package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByFanficId(Long id);
    void save(Comment comment);
}
