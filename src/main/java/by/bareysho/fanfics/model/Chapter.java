package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chapters")
@Getter
@Setter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field
    private String chapterName;

    @Field
    private String text;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChapterRating> ratings;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fanfic_id")
    private Fanfic ovnerFanfic;

    public boolean checkReated(CustomUser customUser){
        boolean check = false;
        for (ChapterRating  rating: ratings){
            if (rating.getUser().getId() == customUser.getId()){
                check = true;
            }
        }
        return check;
    }

    public double calculateAverage(){
        double all = 0;
        if (ratings.size() == 0){
            System.out.println("ne vidit");
            return 0;
        }
        for (ChapterRating chapterRating : ratings){
            all += chapterRating.getAmount();
        }
        return all/ratings.size();
    }
}
