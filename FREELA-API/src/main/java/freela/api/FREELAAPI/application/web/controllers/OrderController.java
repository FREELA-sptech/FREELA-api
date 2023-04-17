package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> createOrder(
            @RequestBody
            @Valid
            OrderRequest order,
            @PathVariable @NotNull Integer userId) {

        if(!this.usersRepository.existsById(userId)){
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.status(201).body(orderService.create(order, userId));
    }


    @PostMapping("/{orderId}/{proposalId}")
    public ResponseEntity<Object> addProposalToOrder(
            @PathVariable @NotNull Integer orderId,
            @PathVariable @NotNull Integer proposalId
    ) {
        Optional<Orders> optionalOrders = this.orderRepository.findById(orderId);

        if(!optionalOrders.isPresent()){
            return ResponseEntity.status(404).body("Order not found");
        }
        if(optionalOrders.get().isAccepted()){
            return ResponseEntity.status(400).body("Order already accepted");
        }

        return ResponseEntity.status(200).body(orderService.addProposalToOrder(orderId, proposalId));
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAll() {
        return ResponseEntity.status(200).body(orderService.getAll());
    }


}
