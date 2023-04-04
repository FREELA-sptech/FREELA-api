package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Users;
import freela.api.FREELAAPI.Repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController{
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<Users> post(@Valid @RequestBody Users user){
        this.usersRepository.save(user);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getALl(){
        List<Users>  users = this.usersRepository.findAll();
        return ResponseEntity.status(200).body(users);
    }




}
