package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
