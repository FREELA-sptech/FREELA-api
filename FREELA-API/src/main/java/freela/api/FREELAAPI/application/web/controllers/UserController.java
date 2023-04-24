package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.LoginRequest;
import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.Users;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Users> post(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.status(201).body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@Valid @RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
           return ResponseEntity.status(200).body(
                    userService.autenticar(usuarioLoginDto));
        } catch (RuntimeException ex){
            throw new RuntimeException("Erro ao realizar login: " + ex);
        }
    }

}
