package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.UpdateUserRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserResponse;
import freela.api.FREELAAPI.application.web.security.jwt.GerenciadorTokenJwt;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.AvaliationService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioMapper;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

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

    @Autowired
    private AvaliationService avaliationService;

    @Autowired
    private OrderService orderService;

    @Override
    public Users register(UserRequest userRequest) {
        String senhaCriptografada = passwordEncoder.encode(userRequest.getPassword());

        Users user = usersRepository.save(
                new Users(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        senhaCriptografada,
                        userRequest.getUserName(),
                        userRequest.getProfilePhoto(),
                        userRequest.getDescription(),
                        userRequest.getUf(),
                        userRequest.getCity(),
                        userRequest.getIsFreelancer()
                )
        );

        this.userInterestService.createUserInterest(userRequest.getSubCategoryId(), user);

        return user;
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Users usuarioAutenticado =
                usersRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public UserResponse getUser(Users user){
        Double rate = avaliationService.getUserAvaliation(user);
        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);
        List<Category> categories = userInterestService.getAllCategoriesByUser(user);

        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getUserName(),
                user.getProfilePhoto(),
                rate,
                user.getUf(),
                user.getCity(),
                categories,
                subCategories);
    }

    public FreelancerResponse getFreelancerUser(Users user){
        Double rate = avaliationService.getUserAvaliation(user);

        List<Orders> concludedOrders = orderService.getConcludedOrders(user);

        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);

        List<Category> categories = userInterestService.getAllCategoriesByUser(user);
        return new FreelancerResponse(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getProfilePhoto(),
                user.getDescription(),
                rate,
                user.getUf(),
                user.getCity(),
                concludedOrders.size(),
                categories,
                subCategories
                );
    }

    @Override
    public FreelancerResponse uploadPicture(Users user, MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();

        // Atualiza o campo profilePhoto
        user.setProfilePhoto(imageData);

        // Salva as alterações no banco de dados
        usersRepository.save(user);

        return getFreelancerUser(user);
    }

    @Override
    public FreelancerResponse updateUser(Users user, UpdateUserRequest userUpdate) {
        user.setName(userUpdate.getName());
        user.setUf(userUpdate.getUf());
        user.setCity(userUpdate.getCity());
        user.setDescription(userUpdate.getDescription());

        // Salva as alterações no banco de dados
        usersRepository.save(user);

        return getFreelancerUser(user);
    }
}
