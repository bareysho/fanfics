package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOvnerFanfic_Id(Long id);
    Comment findById(Long id);
}
