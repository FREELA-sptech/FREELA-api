package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-interest")
public class OrderInterestController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderInterestRepository orderInterestRepository;

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getAllInterestsByOrder(@PathVariable Integer orderId){
        Optional<Orders> order = this.orderRepository.findById(orderId);
        if(!order.isPresent()){
            return ResponseEntity.status(404).body("Order not Found");
        }

        List<OrderInterest> interests =  this.orderInterestRepository.findByOrder(order.get());

        if(interests.isEmpty()){
            return ResponseEntity.status(301).build();
        }
        return ResponseEntity.status(200).body(interests);

    }
}
