package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposals,Integer> {
    List<Proposals> findAllByOriginUser(Users user);
    List<Proposals> findAllByOriginUserAndIsAcceptedTrue(Users users);
    List<Proposals> findAllByOriginUserAndIsRefusedTrue(Users users);

    List<Proposals> findAllByDestinedOrder(Integer destinedOrder);
}
