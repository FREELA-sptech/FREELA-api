package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest,Integer> {
    List<UserInterest> findAllByUser(Users users);
    List<UserInterest> findAllBySubCategory(SubCategory subCategory);

}
