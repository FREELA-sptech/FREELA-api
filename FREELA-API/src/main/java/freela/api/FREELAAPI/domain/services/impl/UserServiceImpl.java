package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.security.jwt.GerenciadorTokenJwt;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioMapper;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserInterestService userInterestService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInterestRepository userInterestRepository;

    @Override
    public User register(UserRequest userRequest) {
        String senhaCriptografada = passwordEncoder.encode(userRequest.getPassword());

        User user = usersRepository.save(
                new User(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        senhaCriptografada,
                        userRequest.getUserName(),
                        userRequest.getFreelancer()
                )
        );

        this.userInterestService.createUserInterest(userRequest.getCategoryId(),user);

        return user;
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        User usuarioAutenticado =
                usersRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }
}
