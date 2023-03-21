package freela.api.FREELAAPI.Repository;

import freela.api.FREELAAPI.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
}
