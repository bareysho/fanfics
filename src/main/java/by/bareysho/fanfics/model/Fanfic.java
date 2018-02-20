package by.bareysho.fanfics.model;


import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "fanfic_tags", joinColumns = @JoinColumn(name = "fanfic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @IndexedEmbedded
    private Set<Tag> tags;

    public String getTegsAsSting(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Tag tag : tags){
            stringBuilder.append(tag.getTagName());
        }
        return stringBuilder.toString();
    }

    public Fanfic(){
        tags = new HashSet<>();
    }

}
