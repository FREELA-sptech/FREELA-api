package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInterestRepository extends JpaRepository<OrderInterest,Integer> {
    List<OrderInterest> findAllByOrder(Order order);
}
