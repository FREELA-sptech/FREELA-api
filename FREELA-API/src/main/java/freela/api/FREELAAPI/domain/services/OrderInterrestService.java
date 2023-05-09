package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface OrderInterrestService {
    List<OrderInterest> findByOrder(Order order);
    void createOrderInterest(Category category, SubCategory subCategory, Order order);
}
