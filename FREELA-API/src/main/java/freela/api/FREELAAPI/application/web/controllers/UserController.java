package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.request.SubCategoriesRequest;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.domain.services.dtos.response.UserDetails;
import freela.api.FREELAAPI.resourses.entities.User;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInterestService userInterestService;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @ApiResponses({
            @ApiResponse(responseCode = "400", description =
                    "Dados incorretos.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado.")
    })
    @PostMapping
    public ResponseEntity<User> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.status(201).body(userService.register(user));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Login incorreto.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Login realizado.")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@Valid @RequestBody UsuarioLoginDto usuarioLoginDto) {
        return ResponseEntity.status(200).body(userService.autenticar(usuarioLoginDto));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Nenhum freelancer disponível.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado.")
    })
    @GetMapping("/freelancer")
    public ResponseEntity<List<UserDetails>> getFreelancer() {
        return ResponseEntity.status(201).body(userService.getFreelancers());
    }

    @GetMapping("/subcategory")
    public ResponseEntity<Object> getUsersBySubCategories(@RequestBody SubCategoriesRequest request){

        if(request.getSubCategories().isEmpty()){
            return ResponseEntity.status(400).body("The subcategries list is empty");
        }

        for(Integer sub : request.getSubCategories()){
            if(!this.subCategoryRepository.existsById(sub)){
                return ResponseEntity.status(404).body("Invalid subCategory id "+ sub);
            }
        }
        
        List<User> user = this.userInterestService.getUsersBySubcategories(request.getSubCategories());

        if(user.isEmpty()){
            ResponseEntity.status(204).body(user);
        }

        return ResponseEntity.status(200).body(user);
    }
}
