package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    @Autowired
    private ProposalRepository proposalRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> post(@PathVariable Integer userId,@RequestBody Proposals proposals){
        Optional<Users> user = this.usersRepository.findById(userId);

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("invalid user");
        }
        proposals.setOriginUser(user.get());
        
        return ResponseEntity.status(201).body(this.proposalRepository.save(proposals));
    }

    @GetMapping
    public ResponseEntity<List<Proposals>> getAll(){
        return ResponseEntity.status(200).body(this.proposalRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Proposals> findById(@PathVariable Integer id){
        return ResponseEntity.of(this.proposalRepository.findById(id));
    }

}
