package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-interest")
public class UserInterestController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserInterestService userInterestService;

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias por usuario.")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getAllByUser(@PathVariable Integer userId){
        Optional<Users> usersOptional = this.usersRepository.findById(userId);

        if(!usersOptional.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.status(200).body(userInterestService.getAllSubCategoriesByUser(usersOptional.get()));

    }
}
