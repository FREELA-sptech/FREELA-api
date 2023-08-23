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
import freela.api.FREELAAPI.application.web.dtos.request.UserLoginRequest;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.UsuarioMapper;
import freela.api.FREELAAPI.application.web.dtos.response.UsuarioTokenResponse;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.UserInterest;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;
    private SubCategoryRepository subCategoryRepository;
    private UserInterestService userInterestService;
    private PasswordEncoder passwordEncoder;
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    private AuthenticationManager authenticationManager;
    private UserInterestRepository userInterestRepository;
    private AvaliationService avaliationService;
    private OrderService orderService;

    public UserServiceImpl(
            UsersRepository usersRepository,
            SubCategoryRepository subCategoryRepository,
            UserInterestService userInterestService,
            PasswordEncoder passwordEncoder,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            AuthenticationManager authenticationManager,
            UserInterestRepository userInterestRepository,
            AvaliationService avaliationService,
            OrderService orderService
    ) {
        this.usersRepository = usersRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.userInterestService = userInterestService;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.userInterestRepository = userInterestRepository;
        this.avaliationService = avaliationService;
        this.orderService = orderService;
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        String senhaCriptografada = passwordEncoder.encode(userRequest.getPassword());

        Optional<Users> userByEmail = usersRepository.findByEmail(userRequest.getEmail());

        if (userByEmail.isPresent()) {
            throw new UserConflictsException("Email já cadastrado!");
        }

        Users user = usersRepository.save(UsuarioMapper.register(userRequest, senhaCriptografada));

        this.userInterestService.createUserInterest(userRequest.getSubCategoryId(), user);
        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);
        Double rate = avaliationService.getUserAvaliation(user);

        return UsuarioMapper.response(user, rate, subCategories);
    }

    public UsuarioTokenResponse autenticar(UserLoginRequest userLoginRequest) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                userLoginRequest.getEmail(), userLoginRequest.getPassword());

        Users usuarioAutenticado = usersRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Email não encontrado!")
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.login(usuarioAutenticado, token);
    }

    public UserResponse getUser(Users user) {
        Double rate = avaliationService.getUserAvaliation(user);
        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);

        return UsuarioMapper.response(user, rate, subCategories);
    }

    public FreelancerResponse getFreelancerUser(Users user) {
        Double rate = avaliationService.getUserAvaliation(user);

        List<Orders> concludedOrders = orderService.getConcludedOrders(user);

        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);

        return UsuarioMapper.freelancerResponse(user, rate, concludedOrders.size(), subCategories);
    }

    @Override
    public FreelancerResponse uploadPicture(Authentication authentication, MultipartFile image) throws IOException {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );;

        byte[] imageData = image.getBytes();

        // Atualiza o campo profilePhoto
        user.setProfilePhoto(imageData);

        // Salva as alterações no banco de dados
        usersRepository.save(user);

        return getFreelancerUser(user);
    }

    @Override
    public FreelancerResponse updateUser(Authentication authentication, UpdateUserRequest userUpdate) {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        user.setName(userUpdate.getName());
        user.setUf(userUpdate.getUf());
        user.setCity(userUpdate.getCity());
        user.setDescription(userUpdate.getDescription());

        this.userInterestService.updateUserInterest(userUpdate.getSubCategoryId(), user);

        // Salva as alterações no banco de dados
        usersRepository.save(user);

        return getFreelancerUser(user);
    }

    public List<FreelancerResponse> getUsersBySubcategories(Authentication authentication) {
        Users userRequest = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );;

        List<SubCategory> subCategories = this.userInterestService.getAllSubCategoriesByUser(userRequest);

        List<FreelancerResponse> users = new ArrayList<>();
        Set<Integer> addedUserIds = new HashSet<>();

        for (SubCategory sub : subCategories) {
            List<UserInterest> interest = this.userInterestRepository.findAllBySubCategory(sub);

            for (UserInterest inte : interest) {
                Users user = inte.getUser();
                if (user.getId() != userRequest.getId() && user.getIsFreelancer() && !addedUserIds.contains(user.getId())) {
                    users.add(getFreelancerUser(user));
                    addedUserIds.add(user.getId());
                }
            }
        }

        return users;
    }
}
