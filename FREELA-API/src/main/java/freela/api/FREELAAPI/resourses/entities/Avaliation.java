package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Avaliation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rate;

    @ManyToOne
    private Users user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
