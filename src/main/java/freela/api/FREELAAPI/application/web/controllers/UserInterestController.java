package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
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

    private final UsersRepository usersRepository;

    private final UserInterestService userInterestService;

    public UserInterestController(UsersRepository usersRepository, UserInterestService userInterestService) {
        this.usersRepository = usersRepository;
        this.userInterestService = userInterestService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias por usuario.")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<SubCategory>> getAllByUser(@PathVariable Integer userId){
        Optional<Users> usersOptional = this.usersRepository.findById(userId);

        if(usersOptional.isEmpty()){
            throw new DataAccessException("Subcategory not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userInterestService.getAllSubCategoriesByUser(usersOptional.get()));

    }
}
