package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

@Entity
@Table(name = "chapters")
@Getter
@Setter
public class Chapter
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field
    private String chapterName;

    @Field
    private String text;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fanfic_id")
    private Fanfic ovnerFanfic;

}
