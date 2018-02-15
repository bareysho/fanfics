//package net.RAD.springsecurityapp.model;
//
//import org.hibernate.search.annotations.Field;
//
//import javax.persistence.*;
//
//import java.util.Set;
//
//@Entity(name="tags")
//public class Tag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "tag_id")
//    private Long id;
//    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
//    private Set<Fanfic> projects;
//    @Column(name = "tag_name")
//    @Field
//    private String tagName;
//
//    public Tag(String tagName, Set<Fanfic> projects) {
//        this.tagName = tagName;
//        this.projects = projects;
//    }
//
//    public Tag(){}
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getTagName() {
//        return tagName;
//    }
//
//    public void setTagName(String tagName) {
//        this.tagName = tagName;
//    }
//
//    public Set<Fanfic> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(Set<Fanfic> projects) {
//        this.projects = projects;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if(o == null || !(o instanceof Tag)) {
//            return false;
//        }
//        return this.tagName.equals(((Tag) o).tagName);
//    }
//}
