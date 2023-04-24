package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    @Autowired
    ProposalService proposalService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @GetMapping
    public ResponseEntity<List<Proposals>> findAll(){
        List<Proposals> proposals = this.proposalRepository.findAll();

        if(proposals.isEmpty()){
            return ResponseEntity.status(203).body(proposals);
        }
        return ResponseEntity.status(200).body(proposals);
    }

    @PostMapping("/{originUserId}/{orderId}")
    public ResponseEntity<Object> post(
            @PathVariable @NotNull int originUserId,
            @PathVariable @NotNull int orderId,
            @RequestBody ProposalRequest proposal
    ){
        Optional<Orders> optionalOrders = this.orderRepository.findById(orderId);

        if(!this.usersRepository.existsById(originUserId)){
            return ResponseEntity.status(404).body("User not found");
        }
        if(!this.orderRepository.existsById(orderId)){
            return ResponseEntity.status(404).body("order not found");
        }
        if(optionalOrders.get().isAccepted()){
            return ResponseEntity.status(400).body("Order already accepted");
        }
        return ResponseEntity.status(201).body(proposalService.create(originUserId, proposal,orderId));
    }


}
