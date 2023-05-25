package freela.api.FREELAAPI.application.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioTokenResponse {
    private Integer id;
    private String token;
    private boolean isFreelancer;
}
