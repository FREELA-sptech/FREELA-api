package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderInterrestServiceImpl implements OrderInterrestService {

    private final OrderRepository orderRepository;
    private final OrderInterestRepository orderInterestRepository;
    private final SubCategoryRepository subCategoryRepository;

    public OrderInterrestServiceImpl(
            OrderRepository orderRepository,
            OrderInterestRepository orderInterestRepository,
            SubCategoryRepository subCategoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderInterestRepository = orderInterestRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public ListaObj<SubCategory> getAllSubCategoriesByUser(Integer id) {
        Orders order = findOrderById(id);
        List<OrderInterest> interests = orderInterestRepository.findAllByOrder(order);

        if (interests.isEmpty()) {
            throw new DataAccessException("Lista de pedidos vazia.", HttpStatus.NOT_FOUND);
        }

        ListaObj<SubCategory> subCategoryListaObj = new ListaObj<>(interests.size());

        interests.stream()
                .map(OrderInterest::getSubCategory)
                .forEach(subCategoryListaObj::adiciona);

        return subCategoryListaObj;
    }

    public void createOrderInterest(ArrayList<Integer> subCategories, Orders order) {
        subCategories.stream()
                .map(subCategoryRepository::findById)
                .flatMap(Optional::stream)
                .forEach(subCategory -> orderInterestRepository.save(new OrderInterest(order, subCategory)));
    }

    public void updateOrderInterest(ArrayList<Integer> subCategories, Orders order){
        this.deleteOrderInterest(order);
        this.createOrderInterest(subCategories,order);
    }

    public void deleteOrderInterest(Orders orders){
        List<OrderInterest> interests = this.orderInterestRepository.findAllByOrder(orders);

        if (interests.isEmpty()){
            throw new DataAccessException("Nenhum interesse encontrado.", HttpStatus.NOT_FOUND);
        }

        this.orderInterestRepository.deleteAll(interests);
    }

    private Orders findOrderById(Integer orderId) {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new DataAccessException("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        }
        return order.get();
    }
}
