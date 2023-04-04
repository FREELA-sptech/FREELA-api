package freela.api.FREELAAPI.Entity;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Users {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer id;

    @NotNull(message = "Name is null")
    private String name;

    @NotNull(message = "Email is null")
    @Email(message = "Email not valid")
    private String email;

    @NotNull(message = "password is null")
    @Min(value = 8, message = "password is lower than 3 characters")
    private String password;

    @NotNull(message = "user_name is null")
    private String userName;

    @NotNull
    @org.hibernate.validator.constraints.br.CPF
    private String CPF;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

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
