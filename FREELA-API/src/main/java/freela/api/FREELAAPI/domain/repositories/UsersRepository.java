package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.domain.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
}
