package freela.api.FREELAAPI.Controller;

import freela.api.FREELAAPI.Entity.Orders;
import freela.api.FREELAAPI.Entity.Proposals;
import freela.api.FREELAAPI.Entity.Users;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order,@PathVariable Integer userId){
        Optional<Users> user = this.usersRepository.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.status(404).build();
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

    @PostMapping("/{userProviderId}/{orderId}/{proposalId}")
    public ResponseEntity<Orders> addProviderUserToOrder(@PathVariable Integer userProviderId,@PathVariable Integer orderId, @PathVariable Integer proposalId){
        Optional<Users> userProvider = this.usersRepository.findById(userProviderId);
        Optional<Proposals> proposal = this.proposalRepository.findById(proposalId);
        Optional<Orders> order = this.orderRepository.findById(orderId);

        if(!userProvider.isPresent() || !order.isPresent() || !proposal.isPresent()){
            return ResponseEntity.status(404).build();
        }
        order.get().setProviderUser(userProvider.get());
        order.get().setAcceptedProposal(proposal.get());
        order.get().setAccepted(true);

        return ResponseEntity.status(200).body(this.orderRepository.save(order.get()));
    }



}
