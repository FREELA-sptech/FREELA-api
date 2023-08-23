package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.UpdateUserRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserResponse;
import freela.api.FREELAAPI.application.web.dtos.request.UserLoginRequest;
import freela.api.FREELAAPI.application.web.dtos.response.UsuarioTokenResponse;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserResponse register(UserRequest userRequest);
    UsuarioTokenResponse autenticar(UserLoginRequest userLoginRequest);
    FreelancerResponse getFreelancerUser(Users user);
    UserResponse getUser(Users user);
    FreelancerResponse uploadPicture(Authentication authentication, MultipartFile image) throws IOException;
    FreelancerResponse updateUser(Authentication authentication, UpdateUserRequest userUpdate);

    List<FreelancerResponse> getUsersBySubcategories(Authentication authentication);
}
