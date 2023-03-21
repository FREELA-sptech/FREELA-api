package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Users;
import freela.api.FREELAAPI.Repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController{
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<Users> post(@RequestBody Users user){
        this.usersRepository.save(user);
        return ResponseEntity.status(201).body(user);
    }

}
