package by.bareysho.fanfics.model;


import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
//@Indexed
//@NoArgsConstructor
//@AllArgsConstructor
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
    private String name;

    @Field
    private int genreId;

    @URL
    private String image;

    @NotNull
    @Field
    @Length(min = 5, max = 100)
    private String description;


}
