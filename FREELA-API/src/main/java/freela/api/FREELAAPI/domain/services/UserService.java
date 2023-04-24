package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.stereotype.Service;

public interface UserService {
    Users register(UserRequest userRequest);
    Users login(String email, String senha);

}
