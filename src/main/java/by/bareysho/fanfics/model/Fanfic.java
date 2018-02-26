package by.bareysho.fanfics.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Table(name = "fanfics")
@Getter
@Setter
public class Fanfic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Length(min = 4)
    @Field
    private String fanficName;

    @URL
    private String image;

    @NotNull
    @Field
    private String description;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "average_rate")
    private Double averageRate;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private CustomUser creator;

    @IndexedEmbedded
    @OneToMany(mappedBy = "ovnerFanfic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Chapter> chapters;

    @IndexedEmbedded
    @OneToMany(mappedBy = "ovnerFanfic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> comments;

    @IndexedEmbedded
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "fanfic_genres", joinColumns = @JoinColumn(name = "fanfic_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @IndexedEmbedded
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "fanfic_tags", joinColumns = @JoinColumn(name = "fanfic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public String getTegsAsSting() {
        String tagsToReturn = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Tag tag : tags) {
            stringBuilder.append(tag.getTagName()).append(",");
        }
        if (stringBuilder.toString().length() > 0) {
            tagsToReturn = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        }

        return tagsToReturn;
    }

    public String getGenresAsSting() {
        String genresToReturn = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : genres) {
            stringBuilder.append(genre.getGenreName()).append(", ");
        }
        if (stringBuilder.toString().length() > 0) {
            genresToReturn = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2);
        }

        return genresToReturn;
    }

    public String getDateAsString(){
        SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");
        return formatForDate.format(creationDate).toString();
    }

    public Fanfic() {
        tags = new HashSet<>();
    }

}
