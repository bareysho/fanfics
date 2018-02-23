package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTagName(String name);
}
