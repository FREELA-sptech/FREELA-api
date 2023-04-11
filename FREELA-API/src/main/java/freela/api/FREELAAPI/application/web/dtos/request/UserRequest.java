package freela.api.FREELAAPI.application.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 2, max = 50)
    @Schema(name = "Nome", description = "Nome do usuario", example = "Joaquim")
    private String name;
    @Email
    @Schema(name = "Email", description = "Email do usuario", example = "joaquim,carvalho@sptech.school")
    private String email;
    @Size(min = 8, max = 50)
    @Schema(name = "Senha", description = "Senha do usuario", example = "#Gf44433366688")
    private String password;
    @Size(min = 4, max = 50)
    @Schema(name = "Apelido ou nickname", description = "Apelidou ou nickname do usuario", example = "joaCarv")
    private String userName;
}
