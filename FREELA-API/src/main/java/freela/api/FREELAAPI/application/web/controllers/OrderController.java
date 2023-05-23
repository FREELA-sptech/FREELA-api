package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.Exception.ErrorReturn;
import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
    @Autowired
    private ProposalRepository proposalRepository;

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem criada.")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<Object> createOrder(
            @RequestBody
            @Valid
            OrderRequest order,
            @PathVariable @NotNull Integer userId) throws IOException {

        if(!this.usersRepository.existsById(userId)){
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.status(201).body(orderService.create(order, userId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem criada.")
    })
    @PostMapping("/upload-pictures/{orderId}")
    public ResponseEntity<Object> uploadPictures(
            @RequestParam("images") List<MultipartFile> images,
            @PathVariable @NotNull Integer orderId,
            Authentication authentication) throws IOException {
        Integer userId = TokenDetailsDto.getUserId(authentication);

        if(!this.usersRepository.existsById(userId)){
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.status(201).body(orderService.updatePictures(images, orderId, userId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "não ordenado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Ordenado pelo maior preco.")
    })
    @GetMapping("/lower-price")
    public ResponseEntity<Object> orderByHigherPrice(){
        return ResponseEntity.status(200).body(this.orderService.orderByHigherPrice());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "não ordenado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Ordenado pelo maior preco.")
    })
    @GetMapping("/by-user")
    public ResponseEntity<Object> orderByUser(Authentication authentication){
        Optional<Users> user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication));


        return ResponseEntity.status(200).body(this.orderService.getOrderByUser(user.get()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Preço não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Preço encontrado.")
    })
    @GetMapping("/indice/{indice}")
    public ResponseEntity<Object> getByPreco(@PathVariable Integer indice){

        ListaObj lista =this.orderService.orderByHigherPrice();

        Optional<Orders> ordersOptional = this.orderRepository.findById(indice);

        return  ResponseEntity.status(200).body(lista.buscaBinaria(ordersOptional));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description =
                    "Pedido ja aceito.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description =
                    "Ordem não encontrada," +
                            "Propostas não encontradas ou " +
                            "Proposta gerada pelo mesmo usuário do pedido.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Adicionado.")
    })
    @PostMapping("/{orderId}/{proposalId}")
    public ResponseEntity<Object> addProposalToOrder(
            @PathVariable @NotNull Integer orderId,
            @PathVariable @NotNull Integer proposalId
    ) {
        Optional<Orders> optionalOrders = this.orderRepository.findById(orderId);
        Optional<Proposals> opttionalProposal= this.proposalRepository.findById(proposalId);

        if(!optionalOrders.isPresent()){
            return ResponseEntity.status(404).body("Order not found");
        }

        if(optionalOrders.get().isAccepted()){
            return ResponseEntity.status(400).body("Order already accepted");
        }

        if(!opttionalProposal.isPresent()){
            return ResponseEntity.status(404).body("Proposals not found");
        }

        if(optionalOrders.get().getUser().getId() == opttionalProposal.get().getOriginUser().getId()){
            return ResponseEntity.status(404).body("Proposal generated by the same user as the order");
        }

        return ResponseEntity.status(200).body(orderService.addProposalToOrder(orderId, proposalId));
    }

    @GetMapping("/edit/{orderId}")
    public ResponseEntity<Object> edit(@PathVariable Integer orderId){
        Optional<Orders> opt = this.orderRepository.findById(orderId);

        if(!opt.isPresent()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order not found"));
        }

        if(opt.get().isAccepted()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order cannot be changed after is accepted"));
        }

        return ResponseEntity.ok().body(this.orderService.edit(opt.get()));
    }

    @PutMapping("update/{orderId}")
    public ResponseEntity<Object> update(@PathVariable Integer orderId, @RequestBody OrderUpdateRequest order){
        Optional<Orders> opt = this.orderRepository.findById(orderId);

        if(!opt.isPresent()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order not found"));
        }

        if(opt.get().isAccepted()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order cannot be changed after is accepted"));
        }

        return ResponseEntity.ok().body(this.orderService.update(order,opt.get().getId()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Lista não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista completa.")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.status(200).body(orderService.getAll());
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<Object> delete(@PathVariable Integer orderId){
        Optional<Orders> order = this.orderRepository.findById(orderId);

        if(!order.isPresent()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order Not Found"));
        }
        return ResponseEntity.status(200).body(this.orderService.delete(order.get()));
    }


}