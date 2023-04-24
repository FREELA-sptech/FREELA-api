package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {
    Orders create(OrderRequest orderRequest, Integer userId);
    Orders addProposalToOrder(Integer orderId,  Integer proposalId);
    List<Orders> getAll();
    ListaObj<Orders> orderByHigherPrice();
}
