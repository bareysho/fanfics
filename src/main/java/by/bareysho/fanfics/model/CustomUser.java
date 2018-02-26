package by.bareysho.fanfics.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min=2, max=30)
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

    public String getRolesAsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Role role: roles){
            stringBuilder.append(role.getRoleName()).append(", ");
        }
        String s = stringBuilder.toString();
        if (stringBuilder.length() > 0){
            s = stringBuilder.toString().substring(0, stringBuilder.length()-2);
        }

        return s;
    }

}
