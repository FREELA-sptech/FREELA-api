package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProposalServiceImpl implements ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Proposals create(Integer originUserId, ProposalRequest proposal,Integer orderId) {
        try {
            Optional<User> user = usersRepository.findById(originUserId);
            Optional<Order> orders = orderRepository.findById(orderId);
            return proposalRepository.save(
                    new Proposals(proposal.getProposalValue(),
                            user.get(),
                            proposal.getDescription(),
                            proposal.getPhoto(),
                            orders.get().getId()));

        } catch (RuntimeException ex){
            throw new RuntimeException("Erro ao cadastrar proposta" + proposal);
        }
    }
}
