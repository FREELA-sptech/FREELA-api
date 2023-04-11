package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.configuration.security.jwt.JwtTokenManager;
import freela.api.FREELAAPI.application.web.dtos.request.LoginRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.UserTokenResponseDto;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManager jwtTokenManager;


    @Override
    public Users register(UserRequest userRequest) {
        final Users user = new Users(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getUserName()
        );

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return usersRepository.save(user);
    }

    public UserTokenResponseDto authenticate(LoginRequest usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Users usuarioAutenticado =
                usersRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenManager.generateToken(authentication);

        return UserTokenResponseDto.of(usuarioAutenticado, token);
    }


}
