package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            Optional<Users> user = usersRepository.findById(originUserId);
            Optional<Orders> orders = orderRepository.findById(orderId);
            return proposalRepository.save(
                    new Proposals(
                            proposal.getProposalValue(),
                            user.get(),
                            proposal.getDescription(),
                            proposal.getDeadline_date(),
                            orders.get().getId(),
                            false,
                            false));
        } catch (RuntimeException ex){
            throw new RuntimeException("Erro ao cadastrar proposta" + proposal);
        }
    }

    public List<Proposals> findProposalsByUser(Integer userId,String clause){
        if(this.usersRepository.existsById(userId)){
            Optional<Users> user = this.usersRepository.findById(userId);

            List<Proposals> proposals = this.proposalRepository.findAllByOriginUser(user.get());

            if(clause.equals("accepted")){
                 proposals = this.proposalRepository.findAllByOriginUserAndIsAcceptedTrue(user.get());
            }

            if(clause.equals("refused")){
                 proposals = this.proposalRepository.findAllByOriginUserAndIsRefusedTrue(user.get());
            }

            return proposals;
        }
        return null;
    }

    public Boolean delete(Proposals proposals){
        this.proposalRepository.delete(proposals);
        return true;
    }

    public Proposals update(Integer proposalId,ProposalUpdate proposalUpdate){
        Optional<Proposals> proposal = this.proposalRepository.findById(proposalId);
        proposal.get().setProposalValue(proposalUpdate.getProposalValue());
        proposal.get().setDescription(proposalUpdate.getDescription());
        proposal.get().setDeadline_date(proposalUpdate.getDeadline_date());

        return this.proposalRepository.save(proposal.get());
    }

    public Boolean refuse(Proposals proposals){
        proposals.setIsAccepted(false);
        proposals.setIsRefused(true);
        this.proposalRepository.save(proposals);
        return true;
    }
}
