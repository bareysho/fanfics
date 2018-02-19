package by.bareysho.fanfics.model;


import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "fanfics")
@Getter
@Setter
public class Fanfic {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Length(min = 3)
    @Field
    private String fanficName;

    @Field
    private int genreId;

    @URL
    private String image;

    @NotNull
    @Field
    @Length(min = 5, max = 500)
    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private CustomUser creator;

    @OneToMany(mappedBy = "ovnerFanfic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Chapter> chapters;

    @OneToMany(mappedBy = "ovnerFanfic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> comments;

}
