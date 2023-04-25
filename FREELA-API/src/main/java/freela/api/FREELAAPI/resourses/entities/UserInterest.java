package freela.api.FREELAAPI.resourses.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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
}
