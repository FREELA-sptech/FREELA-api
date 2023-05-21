package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/order-interest")
public class OrderInterestController {
    private final OrderInterrestService orderInterrestService;

    public OrderInterestController(OrderInterrestService orderInterrestService) {
        this.orderInterrestService = orderInterrestService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Ordem não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Interesses bordenados pelo id.")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<SubCategory[]> getAllInterestsByOrder(@PathVariable @NotNull Integer orderId){
        return ResponseEntity.ok(orderInterrestService.findByOrder(orderId).vetor);
    }

}
