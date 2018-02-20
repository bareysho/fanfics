package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Fanfic> fanfics;

    @Column(name = "tag_name")
    @Field
    private String tagName;

    public Tag(String tagName, Set<Fanfic> projects) {
        this.tagName = tagName;
        this.fanfics = projects;
    }

    public Tag() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Tag)) {
            return false;
        }
        return this.tagName.equals(((Tag) o).tagName);
    }
}
