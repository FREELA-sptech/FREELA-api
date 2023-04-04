package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Proposals;
import freela.api.FREELAAPI.Entity.Users;
import freela.api.FREELAAPI.Repository.OrderRepository;
import freela.api.FREELAAPI.Repository.ProposalRepository;
import freela.api.FREELAAPI.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/{originUserId}")
    public ResponseEntity<Object> post(@PathVariable Integer originUserId,@RequestBody Proposals proposals){
        Optional<Users> originUser = this.usersRepository.findById(originUserId);

        if(!originUser.isPresent()){
            return ResponseEntity.status(404).body("invalid User");
        }
        proposals.setOriginUser(originUser.get());
        return ResponseEntity.status(201).body(this.proposalRepository.save(proposals));
    }

    @GetMapping
    public ResponseEntity<List<Proposals>> getAll(){
        return status(200).body(this.proposalRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Proposals> findById(@PathVariable Integer id){
        return ResponseEntity.of(this.proposalRepository.findById(id));
    }

}
