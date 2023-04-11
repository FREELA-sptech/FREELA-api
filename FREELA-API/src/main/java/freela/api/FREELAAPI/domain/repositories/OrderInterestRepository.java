package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInterestRepository extends JpaRepository<OrderInterest,Integer> {
    List<OrderInterest> findAllByOrder(Orders order);
}
