package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    interface ProposalRepository extends JpaRepository<Proposals,Integer> {

    }
}
