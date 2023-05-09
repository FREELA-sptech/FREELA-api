package freela.api.FREELAAPI.domain.services.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDetails {
    private Integer id;
    @Schema(name = "Nome", description = "Nome do usuário", example = "João Cardoso")
    private String name;
}
