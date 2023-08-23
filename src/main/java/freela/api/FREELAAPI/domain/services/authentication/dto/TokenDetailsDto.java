package freela.api.FREELAAPI.domain.services.authentication.dto;

import org.springframework.security.core.Authentication;

public class TokenDetailsDto {
    public static Integer getUserId(Authentication authentication) {
        UsuarioDetalhesDto usuarioAutenticado = (UsuarioDetalhesDto) authentication.getPrincipal();
        return usuarioAutenticado.getId();
    }
}
