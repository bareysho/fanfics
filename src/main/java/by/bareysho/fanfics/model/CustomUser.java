package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username = "";

    @Column(name = "email")
    private String email = "";

    @Column(name = "password")
    private String password = "";

    @Column(name = "first_name")
    private String firstName = "";

    @Column(name = "last_name")
    private String lastName = "";

    @Transient
    private String confirmPassword;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "profile_img")
    @URL
    private String profileImg;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Fanfic> fanfics;

    @ManyToMany
    @JoinTable(name = "comments_likes", joinColumns = @JoinColumn(name = "liker_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> likedComments;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public CustomUser() {

    }

    public boolean hasRole(String role) {
        boolean has = false;
        for (Role r : roles) {
            if(r.getRoleName().equals(role)){
                has = true;
            }
        }
        return has;
    }

}
