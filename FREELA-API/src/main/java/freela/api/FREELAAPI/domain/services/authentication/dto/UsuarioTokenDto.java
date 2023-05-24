package freela.api.FREELAAPI.domain.services.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioTokenDto {
    private String token;
    private boolean isFreelancer;
}
