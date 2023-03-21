package freela.api.FREELAAPI.Repository;

import freela.api.FREELAAPI.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
}
