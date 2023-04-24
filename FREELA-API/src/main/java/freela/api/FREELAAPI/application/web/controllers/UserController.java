package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.FilterRequest;
import freela.api.FREELAAPI.application.web.dtos.request.LoginRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Não criado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Usuario registrado.")
    })
    @PostMapping
    public ResponseEntity<Users> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.status(201).body(userService.register(user));
    }

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
            throw new RuntimeException("Erro ao realizar cadastro");
        }
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
