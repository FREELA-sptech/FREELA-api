package freela.api.FREELAAPI.application.web.dtos.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {
    @NotBlank(message = "name é um campo obrigatório e deve ser preenchido.")
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
    @NotBlank(message = "password é um campo obrigatório e deve ser preenchido.")
    @Size(min = 8, max = 50)
    private String password;
    @NotBlank(message = "user_name é um campo obrigatório e deve ser preenchido.")
    @Size(min = 4, max = 50)
    private String userName;

    @CPF(message = "CPF inválido.")
    private String cpf;

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
