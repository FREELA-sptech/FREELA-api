package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.resourses.entities.Orders;

public interface OrderService {
    Orders create(OrderRequest request);
}
