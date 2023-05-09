package freela.api.FREELAAPI.domain.repositories;


import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);

    List<User> findByFreelancerTrue();
}
