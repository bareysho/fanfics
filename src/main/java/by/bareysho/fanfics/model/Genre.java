package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "genre_name")
    @Field
    private String genreName;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Fanfic> fanfics;

}
