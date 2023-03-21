package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Orders;
import freela.api.FREELAAPI.Entity.Users;
import freela.api.FREELAAPI.Repository.OrderRepository;
import freela.api.FREELAAPI.Repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController{
    private OrderRepository orderRepository;
    private UsersRepository usersRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order,@PathVariable Integer userId){
        Optional<Users> user = this.usersRepository.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.status(404).build();
        }
        order.setUser(user.get());
        this.orderRepository.save(order);
        return  ResponseEntity.status(201).body(order);
    }

}
