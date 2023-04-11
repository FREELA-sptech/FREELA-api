package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.LoginRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.UserTokenResponseDto;
import freela.api.FREELAAPI.resourses.entities.Users;

public interface UserService {
    Users register(UserRequest userRequest);
    UserTokenResponseDto authenticate(LoginRequest usuarioLoginDto);

}
