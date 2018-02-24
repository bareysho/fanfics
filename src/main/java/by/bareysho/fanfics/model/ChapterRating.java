package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(name = "chapters_ratings")
public class ChapterRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "amount")
    @Min(0)
    @Max(5)
    private int amount;

}
