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
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "User não encontrado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem criada.")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<Orders> createOrder(
            @RequestBody
            @Valid
            OrderRequest order,
            @PathVariable @NotNull Integer userId) {
        return ResponseEntity.created(URI.create("/orders/" + userId))
                .body(orderService.create(order, userId));
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
        return ResponseEntity.ok(orderService.addProposalToOrder(orderId, proposalId));
    }

    @GetMapping("/edit/{orderId}")
    public ResponseEntity<Object> edit(@PathVariable Integer orderId){
        return ResponseEntity.ok(this.orderService.edit(orderId));
    }

    @PutMapping("update/{orderId}")
    public ResponseEntity<Object> update(@PathVariable Integer orderId, @RequestBody OrderUpdateRequest order){
        return ResponseEntity.ok(this.orderService.update(order,orderId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Lista não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista completa.")
    })
    @GetMapping
    public ResponseEntity<List<Orders>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<Object> delete(@PathVariable Integer orderId){
        return ResponseEntity.ok(this.orderService.delete(orderId));
    }


}
