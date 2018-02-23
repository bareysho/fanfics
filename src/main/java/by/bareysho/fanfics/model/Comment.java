package by.bareysho.fanfics.model;

import by.bareysho.fanfics.util.DateCounter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Length(min=1)
    @Field
    private String text;

    @Field
    private String date;

    @ManyToMany(mappedBy = "likedComments", fetch = FetchType.LAZY)
    private Set<CustomUser> userWhoLiked;

    private String userCreator;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fanfic_id")
    private Fanfic ovnerFanfic;

    public Comment(){
        date = DateCounter.dateNow();
    }
}