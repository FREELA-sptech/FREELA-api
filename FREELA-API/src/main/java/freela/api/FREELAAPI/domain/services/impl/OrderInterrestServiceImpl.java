package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderInterrestServiceImpl implements OrderInterrestService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderInterestRepository orderInterestRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public List<OrderInterest> findByOrder(Order order) {
        return this.orderInterestRepository.findAllByOrder(order);
    }

    @Override

/*    public ListaObj<SubCategory> findByOrder(Integer id) {
        try {
            Optional<Order> order = this.orderRepository.findById(id);
            List<OrderInterest> interests =  this.orderInterestRepository.findAllByOrder(order.get());
            ListaObj<SubCategory> subCategoryListaObj = new ListaObj<>(interests.size());

            for(OrderInterest orderInterest : interests){
                subCategoryListaObj.adiciona(orderInterest.getSubCategory());
            }


              return subCategoryListaObj;
        }catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }*/
    public void createOrderInterest(Category category, SubCategory subCategory, Order order){
        this.orderInterestRepository.save(
                new OrderInterest(
                        category,
                        order,
                        subCategory
                )
        );
    }
}
