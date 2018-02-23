package by.bareysho.fanfics.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Set<CustomUser> users;

    @Column(name = "role_name")
    private String roleName;

    public Role(){
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || !(o instanceof Role)) {
//            return false;
//        }
//        return this.name.equals(((Role) o).name);
//    }
}
