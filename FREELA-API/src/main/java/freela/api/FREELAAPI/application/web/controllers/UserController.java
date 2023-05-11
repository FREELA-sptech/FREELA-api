package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.request.SubCategoriesRequest;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.Users;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInterestService userInterestService;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UsersRepository usersRepository;

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Não criado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Usuario registrado.")
    })
    @PostMapping
    public ResponseEntity<Users> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.status(201).body(userService.register(user));
    }

//    @PostMapping
//    public sendAvaliation(){
//
//    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Login dando erro.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Login realizado.")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@Valid @RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
            return ResponseEntity.status(200).body(
                    userService.autenticar(usuarioLoginDto));
        } catch (RuntimeException ex){
            throw new RuntimeException("Erro ao realizar login: " + ex);
        }
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

        List<Users> users = this.userInterestService.getUsersBySubcategories(request.getSubCategories());

        if(users.isEmpty()){
            ResponseEntity.status(204).body(users);
        }

        return ResponseEntity.status(200).body(users);
    }
    @GetMapping("/edit/{userId}")
    public ResponseEntity<Object> edit(@PathVariable Integer userId){
        Optional<Users> user = this.usersRepository.findById(userId);

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }

        if(user.get().getIsFreelancer()){
            return ResponseEntity.status(200).body(userService.getFreelancerUser(user.get()));
        }
        return null;
    }

    @PostMapping(value = "/upload-image/{userId}")
    public ResponseEntity<Object> edit(@RequestParam("image") MultipartFile image, @PathVariable Integer userId) throws IOException {
        Optional<Users> user = this.usersRepository.findById(userId);

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.status(200).body(userService.uploadPicture(user.get(), image));
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
