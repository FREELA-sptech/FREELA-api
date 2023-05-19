package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.Exception.UserConflictsException;
import freela.api.FREELAAPI.application.web.Exception.UserNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Optional<Users> userByEmail = usersRepository.findByEmail(userRequest.getEmail());

        if (userByEmail.isPresent()) {
            throw new UserConflictsException("Email já cadastrado!");
        }


        Users user = usersRepository.save(
                new Users(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        senhaCriptografada,
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

        Users usuarioAutenticado = usersRepository.findByEmail(usuarioLoginDto.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Email não encontrado!")
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

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

        List<Category> categoriesData = userInterestService.getAllCategoriesByUser(user);

        List<Category> categories = categoriesData.stream()
                .distinct()
                .collect(Collectors.toList());

        return new FreelancerResponse(
                user.getId(),
                user.getName(),
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

        this.userInterestService.updateUserInterest(userUpdate.getSubCategoryId(), user);

        // Salva as alterações no banco de dados
        usersRepository.save(user);

        return getFreelancerUser(user);
    }
}
