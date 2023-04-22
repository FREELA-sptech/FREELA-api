package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.OrderInterestRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
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

    public ListaObj<SubCategory> findByOrder(Integer id) {
        try {
            Optional<Orders> order = this.orderRepository.findById(id);
            List<OrderInterest> interests =  this.orderInterestRepository.findAllByOrder(order.get());
            ListaObj<SubCategory> subCategoryListaObj = new ListaObj<>(interests.size());

            for(OrderInterest orderInterest : interests){
                subCategoryListaObj.adiciona(orderInterest.getSubCategory());
            }


            return subCategoryListaObj;
        }catch (RuntimeException ex){
            throw new RuntimeException("Erro ao processar operação com id: " + id);
        }
    }
    public void createOrderInterest(ArrayList<Integer> subCategories, Orders order){

        for(Integer subCategoryid : subCategories){
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(subCategoryid);
            subCategory.ifPresent(category -> this.orderInterestRepository.save(
                    new OrderInterest(
                            order.getCategory(),
                            order,
                            category
                    )
            ));
        }
    }
}
