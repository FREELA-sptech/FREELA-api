package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderInterrestServiceImplTest {
    OrderInterestRepository orderInterestRepository = Mockito.mock(OrderInterestRepository.class);
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    SubCategoryRepository subCategoryRepository = Mockito.mock(SubCategoryRepository.class);
    OrderInterrestServiceImpl orderInterestService = new OrderInterrestServiceImpl(orderRepository, orderInterestRepository, subCategoryRepository);

    @Test
    public void testGetAllSubCategoriesByUser_Success() {
        Orders order = new Orders(
                "Descrição do Pedido 1",
                "Título do Pedido 1",
                150.0,
                "2023-05-30",
                new Users()
        );
        
        order.setId(1);
        order.setAccepted(true);

        OrderInterest interest1 = new OrderInterest();
        interest1.setSubCategory(new SubCategory());
        OrderInterest interest2 = new OrderInterest();
        interest2.setSubCategory(new SubCategory());

        List<OrderInterest> interests = new ArrayList<>();
        interests.add(interest1);
        interests.add(interest2);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderInterestRepository.findAllByOrder(order)).thenReturn(interests);

        ListaObj<SubCategory> result = orderInterestService.getAllSubCategoriesByUser(order.getId());

        assertNotNull(result);
        assertEquals(interests.size(), result.getTamanho());

        verify(orderInterestRepository, times(1)).findAllByOrder(order);
    }

    @Test
    public void testGetAllSubCategoriesByUser_Failure() {
        Orders order = new Orders();
        order.setId(1);

        List<OrderInterest> interests = new ArrayList<>();

        when(orderInterestRepository.findAllByOrder(order)).thenReturn(interests);

        try {
            orderInterestService.getAllSubCategoriesByUser(order.getId());
            fail("Expected DataAccessException to be thrown");
        } catch (DataAccessException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
            assertEquals("Pedido não encontrado.", ex.getMessage());

        }
    }
}