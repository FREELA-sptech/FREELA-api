package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    @Autowired
    ProposalService proposalService;
    @PostMapping
    public ResponseEntity<Object> post(@RequestParam @NotNull Integer originUserId, @RequestBody Proposals proposal){
        return ResponseEntity.status(201).body(proposalService.create(originUserId, proposal));
    }


}
