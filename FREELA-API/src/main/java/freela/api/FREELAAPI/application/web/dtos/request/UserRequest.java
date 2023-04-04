package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserRequest {
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
    @Size(min = 8, max = 50)
    private String password;
    @Size(min = 4, max = 50)
    private String userName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
