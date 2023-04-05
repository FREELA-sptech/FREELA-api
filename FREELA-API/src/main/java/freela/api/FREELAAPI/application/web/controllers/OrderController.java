package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequest order, @PathVariable Integer userId){
        Optional<Users> user = this.usersRepository.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.status(404).build();
        }
        ArrayList<Integer> subCategoryIds =  order.getSubCategoryIds();
//
//        for(Integer subId : subCategoryIds ){
//
//        }

        Orders newOrder = this.orderService.create(order);
        return  ResponseEntity.status(201).body(newOrder);
    }

    @PostMapping("/{orderId}/{proposalId}")
    public ResponseEntity<Object> addProposalToOrder(@PathVariable Integer orderId, @PathVariable Integer proposalId){
        Optional<Orders> order = this.orderRepository.findById(orderId);
        Optional<Proposals> proposals = this.proposalRepository.findById(proposalId);

        if(!order.isPresent()){
            return ResponseEntity.status(404).body("order not found");
        }

        if(!proposals.isPresent()){
            return ResponseEntity.status(404).body("proposal not found");
        }

        if(order.get().isAccepted()){
            return ResponseEntity.status(400).body("Order already accepted");
        }

        order.get().setProposals(proposals.get());
        order.get().setAccepted(true);
        return ResponseEntity.status(200).body(this.orderRepository.save(order.get()));
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAll(){
        List<Orders> orders =  this.orderRepository.findAll();
        if(orders.isEmpty()){
            return ResponseEntity.status(203).build();
        }
        return ResponseEntity.status(200).body( this.orderRepository.findAll());
    }


}
