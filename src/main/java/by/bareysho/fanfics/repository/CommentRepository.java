package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOvnerFanfic_Id(Long id);
}
