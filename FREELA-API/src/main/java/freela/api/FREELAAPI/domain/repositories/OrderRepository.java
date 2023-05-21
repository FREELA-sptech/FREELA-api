package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> getAllByUser(Users  user);
    List<Orders> findALlByUserAndIsAcceptedTrue(Users user);
    List<Orders> findAllByUser(Users user);
}
