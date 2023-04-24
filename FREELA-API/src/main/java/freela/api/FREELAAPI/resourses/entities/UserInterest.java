package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;

@Entity
public class UserInterest {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Users user;

    @ManyToOne
    private SubCategory subCategory;

    public UserInterest(Users user, SubCategory subCategory) {
        this.user = user;
        this.subCategory = subCategory;
    }

    public UserInterest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
