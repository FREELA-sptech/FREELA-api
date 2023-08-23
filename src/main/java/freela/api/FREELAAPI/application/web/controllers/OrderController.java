package freela.api.FREELAAPI.application.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import freela.api.FREELAAPI.application.web.Exception.ErrorReturn;
import freela.api.FREELAAPI.application.web.Exception.UserNotFoundException;
import freela.api.FREELAAPI.application.web.dtos.request.MessageRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderCreatedResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.dtos.response.PhotosResponse;
import freela.api.FREELAAPI.application.web.helpers.FilaObj;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.application.web.helpers.PilhaObj;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.resourses.entities.OrderPhotos;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
            @ApiResponse(responseCode = "404", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem criada.")
    })
    @PostMapping
    public ResponseEntity<OrderCreatedResponse> createOrder(
            @RequestBody
            @Valid
            OrderRequest order,
            Authentication authentication) throws IOException {
        return ResponseEntity.ok(orderService.create(order, authentication));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem criada.")
    })
    @PostMapping("/upload-pictures/{orderId}")
    public ResponseEntity<OrderCreatedResponse> uploadPictures(
            @RequestParam("images") List<MultipartFile> images,
            @PathVariable @NotNull Integer orderId) throws IOException {
        return ResponseEntity.ok(orderService.updatePictures(images, orderId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "não ordenado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Ordenado pelo maior preco.")
    })
    @GetMapping("/by-user")
    public ResponseEntity<Object> orderByUser(Authentication authentication,
                                              @RequestParam (required = false) String accepted ){

        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        if (accepted != null) {
            return ResponseEntity.ok(this.orderService.getConcludedOrders(user));
        }

        return ResponseEntity.ok(this.orderService.getOrderByUser(authentication));
    }

    @GetMapping("/by-user-id/{id}")
    public ResponseEntity<List<OrderResponse>> getByUserId(@PathVariable Integer id){
        List<OrderResponse> orders = this.orderService.findAllByUserId(id);
        if(orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
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

        return ResponseEntity.ok().body(this.orderService.edit(opt.get()));
    }

    @GetMapping("/extrato")
    public byte[] gerarExtrato(Authentication authentication){
        return this.orderService.getUserOrdersExtract(authentication);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Object> update(@PathVariable Integer orderId, @RequestBody OrderUpdateRequest order){
        Optional<Orders> opt = this.orderRepository.findById(orderId);

        if(opt.isEmpty()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order not found"));
        }

        if(opt.get().isAccepted()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order cannot be changed after is accepted"));
        }

        return ResponseEntity.ok().body(this.orderService.update(order,opt.get().getId()));
    }

    @PutMapping("/update-pictures/{orderId}")
    public ResponseEntity<Object> updatePictures(
            @RequestParam(value = "newPhotos", required = false)List<MultipartFile> newPhotos,
            @RequestParam(value = "deletedPhotos", required = false) List<Integer> deletedPhotos,
            @PathVariable Integer orderId) throws IOException {
        Optional<Orders> opt = this.orderRepository.findById(orderId);

        if(opt.isEmpty()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order not found"));
        }

        if(opt.get().isAccepted()){
            return ResponseEntity.status(404).body(new ErrorReturn("Order cannot be changed after is accepted"));
        }

        return ResponseEntity.ok().body(this.orderService.updatePictures(newPhotos, deletedPhotos, orderId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Lista não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista completa.")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(Authentication authentication, @RequestParam(required = false, name = "orderType") String orderType) {
        return ResponseEntity.status(200).body(orderService.getAllOrdersBySubCategoriesUser(authentication, orderType));
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<Object> delete(@PathVariable Integer orderId){
        Optional<Orders> order = this.orderRepository.findById(orderId);

        return order.<ResponseEntity<Object>>map(
                orders -> ResponseEntity.status(200).body(this.orderService.delete(orders))
        ).orElseGet(() -> ResponseEntity.status(404).body(new ErrorReturn("Order Not Found")));
    }

    private PilhaObj<Orders> pilha;
    private FilaObj<Orders> fila;


    public void OrdersResource() {
        pilha = new PilhaObj<>(10);
        fila = new FilaObj<>(10);

    }
    @PostMapping("/pilha")
    public ResponseEntity<String> addOrder(@RequestBody Orders order) {
        pilha.push(order);
        return ResponseEntity.ok("Pedido adicionado à pilha com sucesso!");
    }

    @GetMapping("/pilha/pop")
    public ResponseEntity<Orders> popOrder() {
        Orders order = pilha.pop();
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pilha/peek")
    public ResponseEntity<Orders> peekOrderPilha() {
        Orders order = pilha.peek();
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pilha/list")
    public ResponseEntity<List<Orders>> listOrdersFila() {
        List<Orders> orderList = new ArrayList<>();
        while (!pilha.isEmpty()) {
            orderList.add(pilha.pop());
        }
        Collections.reverse(orderList);
        return ResponseEntity.ok(orderList);
    }

    @PostMapping("/fila")
    public ResponseEntity<String> enqueueOrder(@RequestBody Orders order) {
        fila.insert(order);
        return ResponseEntity.ok("Pedido adicionado à fila com sucesso!");
    }

    @GetMapping("/fila/dequeue")
    public ResponseEntity<Orders> dequeueOrder() {
        Orders order = fila.poll();
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fila/peek")
    public ResponseEntity<Orders> peekOrder() {
        Orders order = fila.peek();
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fila/list")
    public ResponseEntity<List<Orders>> listOrders() {
        List<Orders> orderList = new ArrayList<>();
        while (!fila.isEmpty()) {
            orderList.add(fila.poll());
        }
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses = this.orderService.getAllOrders();
        if(responses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

}