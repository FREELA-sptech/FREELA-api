package freela.api.FREELAAPI.domain.services.authentication.dto;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.domain.services.dtos.response.UserDetails;
import freela.api.FREELAAPI.resourses.entities.User;

public class UsuarioMapper {

    public static User of(UserRequest usuarioCriacaoDto) {
        User usuario = new User();

        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setName(usuarioCriacaoDto.getName());
        usuario.setPassword(usuarioCriacaoDto.getPassword());

        return usuario;
    }

    public static UsuarioTokenDto of(User usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getName());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setFreelancer(usuario.getFreelancer());

        return usuarioTokenDto;
    }

    public static UserDetails of(User user) {
        UserDetails userDetails = new UserDetails();

        userDetails.setId(user.getId());
        userDetails.setName(user.getName());

        return userDetails;
    }
}