package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;

import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import freela.api.FREELAAPI.resourses.entities.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProposalRepository proposalRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> createOrder(@RequestBody Orders order,@PathVariable Integer userId){
        Optional<Users> user = this.usersRepository.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }
        order.setOringinUser(user.get());
        this.orderRepository.save(order);
        return  ResponseEntity.status(201).body(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAll(){
        List<Orders> orders =  orderRepository.findAll();
        return ResponseEntity.status(200).body(orders);
    }

    @PostMapping("/{orderId}/{proposalId}")
    public ResponseEntity<Object> addProviderUserToOrder(@PathVariable Integer orderId, @PathVariable Integer proposalId){
        Optional<Proposals> proposal = this.proposalRepository.findById(proposalId);
        Optional<Orders> order = this.orderRepository.findById(orderId);

        if(!order.isPresent()){
            return ResponseEntity.status(404).body("invalid Order");
        }

        if(!proposal.isPresent()){
            return ResponseEntity.status(404).body("invalid proposal");
        }

        if(proposal.get().getOriginUser() == order.get().getOringinUser()){
            return ResponseEntity.status(404).body("Order user can´t be the same as the Proposal user");
        }

        order.get().setAcceptedProposal(proposal.get());
        order.get().setAccepted(true);

        return ResponseEntity.status(200).body(this.orderRepository.save(order.get()));
    }



}
