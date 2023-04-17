package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email
    @Schema(name = "Email", description = "Email do usuario para o login", example = "nome.sobrenome@gmail.com")
    private String email;
    @Size(min = 8, max = 50)
    @Schema(name = "Senha", description = "Senha do usuario para o login", example = "#Gf37765489000")
    private String password;
}
