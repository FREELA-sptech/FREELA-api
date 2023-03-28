package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.domain.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
}
