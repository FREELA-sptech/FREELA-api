package freela.api.FREELAAPI.domain.services.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioLoginDto {

    @Schema(description = "E-mail do usuário", example = "john@doe.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "123456")
    private String password;
}