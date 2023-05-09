package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {

}
