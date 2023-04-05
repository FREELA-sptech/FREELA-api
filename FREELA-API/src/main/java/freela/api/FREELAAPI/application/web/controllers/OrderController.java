package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.domain.repositories.*;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderInterestController interestController;



    @PostMapping("/{userId}")
    public ResponseEntity<Object> createOrder(
            @RequestBody
            @Valid
            OrderRequest order,
            @PathVariable Integer userId)
    {

        Optional<Users> user = this.usersRepository.findById(userId);
        Optional<Category> category = this.categoryRepository.findById(order.getCategory());

        if(!user.isPresent()){
            return ResponseEntity.status(404).body("User not found");
        }
        if(!category.isPresent()){
            return ResponseEntity.status(404).body("Invalid Category");
        }

        ArrayList<Integer> subCategoryIds =  order.getSubCategoryIds();
        Orders newOrder = this.orderService.create(order,category.get(),user.get());
        interestController.createOrderInterest(subCategoryIds,newOrder);

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
