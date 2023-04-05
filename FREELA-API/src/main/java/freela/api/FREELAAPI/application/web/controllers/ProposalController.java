package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UsersRepository usersRepository;


    @PostMapping("/{originUserId}")
    public ResponseEntity<Object> post(@PathVariable Integer originUserId, @RequestBody Proposals proposal){
        Optional<Users> user = this.usersRepository.findById(originUserId);

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }
        proposal.setOriginUser(user.get());

        return ResponseEntity.status(201).body(this.proposalRepository.save(proposal));
    }


}
