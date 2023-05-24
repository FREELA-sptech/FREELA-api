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
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserInterestService userInterestService;
    private final SubCategoryRepository subCategoryRepository;
    private final UsersRepository usersRepository;

    public UserController(UserService userService,
                          UserInterestService userInterestService,
                          SubCategoryRepository subCategoryRepository,
                          UsersRepository usersRepository
    ) {
        this.userService = userService;
        this.userInterestService = userInterestService;
        this.subCategoryRepository = subCategoryRepository;
        this.usersRepository = usersRepository;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Não criado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Usuario registrado.")
    })
    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Login dando erro.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Login realizado.")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@Valid @RequestBody UsuarioLoginDto usuarioLoginDto) {
            return ResponseEntity.ok(userService.autenticar(usuarioLoginDto));
    }

    @GetMapping("/by-subcategories")
    public ResponseEntity<Object> getUsersBySubCategories(Authentication authentication){
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));
        List<SubCategory> subCategories = this.userInterestService.getAllSubCategoriesByUser(user.get());

        if(subCategories.isEmpty()){
            return ResponseEntity.status(400).body("The subcategries list is empty");
        }

        List<FreelancerResponse> users = this.userInterestService.getUsersBySubcategories(subCategories, user.get());

        if(users.isEmpty()){
            ResponseEntity.status(204).body(users);
        }

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/details")
    public ResponseEntity<Object> details(Authentication authentication) {
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        if(user.get().getIsFreelancer()){
            return ResponseEntity.status(200).body(userService.getFreelancerUser(user.get()));
        }

        return ResponseEntity.status(200).body(userService.getUser(user.get()));
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image, Authentication authentication) throws IOException {
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        return ResponseEntity.status(200).body(userService.uploadPicture(user.get(), image));
    }

    @PatchMapping
    public ResponseEntity<Object> update(Authentication authentication, @RequestBody UpdateUserRequest userUpdate) {
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        return ResponseEntity.status(200).body(userService.updateUser(user.get(), userUpdate));
    }


//
//    @GetMapping
//    public ResponseEntity<Object> filterUserList(@RequestBody FilterRequest filterRequest){
//
//            if(filterRequest.getNome() != null){
//            }
//            if(filterRequest.getUserName() != null){
//            }
//            if(filterRequest.getEmail() != null){
//            }
//            if(filterRequest.getSubCategoriesId() != null){
//            }
//
//
//    }

}
