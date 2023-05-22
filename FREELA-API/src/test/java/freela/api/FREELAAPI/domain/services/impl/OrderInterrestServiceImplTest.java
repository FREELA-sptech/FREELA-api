package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderInterrestServiceImplTest {
    OrderInterestRepository orderInterestRepository = Mockito.mock(OrderInterestRepository.class);
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    SubCategoryRepository subCategoryRepository = Mockito.mock(SubCategoryRepository.class);
    OrderInterrestServiceImpl orderInterestService = new OrderInterrestServiceImpl(orderRepository, orderInterestRepository, subCategoryRepository);

    @Test
    void testFindByOrder_Success() {

        Integer orderId = 1;
        Orders order = new Orders();
        order.setId(orderId);

        List<OrderInterest> interests = new ArrayList<>();
        SubCategory subCategory1 = new SubCategory();
        SubCategory subCategory2 = new SubCategory();
        interests.add(createOrderInterest(subCategory1));
        interests.add(createOrderInterest(subCategory2));

        when(orderInterestRepository.findAllByOrder(order)).thenReturn(interests);

        ListaObj<SubCategory> result = orderInterestService.findByOrder(orderId);

        // Verificação do resultado
        assertEquals(interests.size(), result.getTamanho());
        assertEquals(subCategory1, result.getElemento(0));
        assertEquals(subCategory2, result.getElemento(1));
    }

    private OrderInterest createOrderInterest(SubCategory subCategory) {
        OrderInterest orderInterest = new OrderInterest();
        orderInterest.setSubCategory(subCategory);
        return orderInterest;
    }

}