package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.UpdateUserRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserResponse;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.application.web.dtos.request.UserLoginRequest;
import freela.api.FREELAAPI.application.web.dtos.response.UsuarioTokenResponse;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final UserService userService;
    private final UsersRepository usersRepository;

    public UserController(UserService userService,
                          UsersRepository usersRepository) {
        this.userService = userService;
        this.usersRepository = usersRepository;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Parâmetros incorretos.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Usuário registrado.")
    })
    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Login realizado.")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.autenticar(userLoginRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "A lista de subcategorias está vazia."),
            @ApiResponse(responseCode = "200", description = "Lista de usuários por subcategorias obtida.")
    })
    @GetMapping("/by-subcategories")
    public ResponseEntity<Object> getUsersBySubCategories(Authentication authentication) {
        return ResponseEntity.ok(this.userService.getUsersBySubcategories(authentication));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Detalhes do usuário obtidos.")
    })
    @GetMapping("/details")
    public ResponseEntity<Object> details(Authentication authentication) {
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));

        if (!user.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        if (user.get().getIsFreelancer()) {
            return ResponseEntity.status(200).body(userService.getFreelancerUser(user.get()));
        }

        return ResponseEntity.status(200).body(userService.getUser(user.get()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Imagem enviada com sucesso.")
    })
    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image, Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.uploadPicture(authentication, image));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.")
    })
    @PatchMapping
    public ResponseEntity<Object> update(Authentication authentication, @RequestBody UpdateUserRequest userUpdate) {
        return ResponseEntity.ok(userService.updateUser(authentication, userUpdate));
    }
}

