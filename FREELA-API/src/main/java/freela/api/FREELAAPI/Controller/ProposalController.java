package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Proposals;
import freela.api.FREELAAPI.Repository.OrderRepository;
import freela.api.FREELAAPI.Repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    @Autowired
    private ProposalRepository proposalRepository;

    @PostMapping
    public ResponseEntity<Proposals> post(@RequestBody Proposals proposals){
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
