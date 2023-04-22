package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface OrderInterrestService {
    List<SubCategory> findByOrder(Integer id);
     void createOrderInterest(ArrayList<Integer> subCategories, Orders order);
}
