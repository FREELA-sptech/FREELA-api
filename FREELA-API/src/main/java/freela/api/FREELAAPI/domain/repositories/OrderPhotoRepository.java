package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.OrderInterest;
import freela.api.FREELAAPI.resourses.entities.OrderPhotos;
import freela.api.FREELAAPI.resourses.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPhotoRepository extends JpaRepository<OrderPhotos,Integer> {
    List<OrderPhotos> findAllByOrder(Orders order);
    void deleteById(Integer id);
}
