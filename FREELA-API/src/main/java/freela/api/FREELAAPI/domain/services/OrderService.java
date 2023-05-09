package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.services.dtos.response.OrderResponse;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest orderRequest, Integer userId);
    Order addProposalToOrder(Integer orderId,  Integer proposalId);
    List<OrderResponse> getAll();
    ListaObj<Order> orderByHigherPrice();
}
