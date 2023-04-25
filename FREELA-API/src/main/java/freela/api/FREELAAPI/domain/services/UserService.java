package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.stereotype.Service;

public interface UserService {
    Users register(UserRequest userRequest);
    UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto);

}
