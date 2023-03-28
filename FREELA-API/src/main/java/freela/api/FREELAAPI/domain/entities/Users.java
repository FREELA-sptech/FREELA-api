package freela.api.FREELAAPI.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String userName;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public Users(Integer id, String name, String email, String password, String userName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public Users() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
