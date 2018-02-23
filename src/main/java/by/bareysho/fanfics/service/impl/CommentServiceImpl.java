package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Comment;
import by.bareysho.fanfics.repository.CommentRepository;
import by.bareysho.fanfics.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findByFanficId(Long id) {
        return commentRepository.findByOvnerFanfic_Id(id);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id);
    }
}
