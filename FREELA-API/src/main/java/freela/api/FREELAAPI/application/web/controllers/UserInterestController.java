package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.Users;
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


    @GetMapping("/{userId}")
    public ResponseEntity<Object> getAllByUser(@PathVariable Integer userId){
        Optional<Users> usersOptional = this.usersRepository.findById(userId);

        if(!usersOptional.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.status(200).body(userInterestService.getAllSubCategoriesByUser(usersOptional.get()));

    }
}
