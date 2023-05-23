package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
        interests.add(createOrderInterest(1, new SubCategory()));
        interests.add(createOrderInterest(2, new SubCategory()));
        interests.add(createOrderInterest(3, new SubCategory()));

        when(orderInterestRepository.findAllByOrder(order)).thenReturn(interests);

        ListaObj<SubCategory> result = orderInterestService.findByOrder(orderId);

        assertNotNull(result);
        assertEquals(interests.size(), result.getTamanho());
    }

    @Test
    void testFindByOrder_EmptyList() {
        Integer orderId = 1;
        Orders order = new Orders();
        order.setId(orderId);

        List<OrderInterest> interests = new ArrayList<>();

        when(orderInterestRepository.findAllByOrder(order)).thenReturn(interests);


        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            orderInterestService.findByOrder(orderId);
        });

        assertEquals("Pedido não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    private OrderInterest createOrderInterest(Integer id, SubCategory subCategory) {
        OrderInterest orderInterest = new OrderInterest();
        orderInterest.setId(id);
        orderInterest.setSubCategory(subCategory);
        return orderInterest;
    }


}