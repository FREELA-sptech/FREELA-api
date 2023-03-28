package freela.api.FREELAAPI.Repository;

import freela.api.FREELAAPI.Entity.Orders;
import freela.api.FREELAAPI.Entity.Proposals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    interface ProposalRepository extends JpaRepository<Proposals,Integer> {

    }
}
