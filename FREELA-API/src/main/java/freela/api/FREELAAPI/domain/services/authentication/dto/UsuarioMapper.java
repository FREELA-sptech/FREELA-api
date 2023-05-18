package freela.api.FREELAAPI.domain.services.authentication.dto;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.resourses.entities.Users;

public class UsuarioMapper {
    public static Users of(UserRequest usuarioCriacaoDto) {
        Users usuario = new Users();

        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setName(usuarioCriacaoDto.getName());
        usuario.setPassword(usuarioCriacaoDto.getPassword());

        return usuario;
    }

    public static UsuarioTokenDto of(Users usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setNome(usuario.getName());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setFreelancer(usuario.getIsFreelancer());

        return usuarioTokenDto;
    }
}