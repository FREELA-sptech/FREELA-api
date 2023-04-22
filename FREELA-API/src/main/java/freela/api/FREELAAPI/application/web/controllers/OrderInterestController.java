package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
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
    @Autowired
    private OrderInterrestService orderInterrestService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<SubCategory>> getAllInterestsByOrder(@PathVariable @NotNull Integer orderId){
        return ResponseEntity.status(200).body(orderInterrestService.findByOrder(orderId));
    }

}
