package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.exceptions.ProposalAcceptedException;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository proposalRepository;
    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;

    public ProposalServiceImpl(
            ProposalRepository proposalRepository,
            UsersRepository usersRepository,
            OrderRepository orderRepository
    ) {
        this.proposalRepository = proposalRepository;
        this.usersRepository = usersRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public Proposals create(Integer originUserId, ProposalRequest proposal, Integer orderId) {
        try {
            Users user = findUserById(originUserId);
            Orders orders = findOrderById(orderId);
            return proposalRepository.save(
                    new Proposals(
                            proposal.getProposalValue(),
                            user,
                            proposal.getDescription(),
                            proposal.getDeadline_date(),
                            orders.getId(),
                            false,
                            false));
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao cadastrar proposta" + proposal);
        }
    }

    public List<Proposals> findProposalsByUser(Integer userId, String clause) {
        Users user = findUserById(userId);
        return proposalRepository.findAllByOriginUserAndIsRefusedTrue(user);
    }

    public Boolean delete(Integer id) {
        this.proposalRepository.delete(findProposalById(id));
        return true;
    }

    public Proposals update(Integer proposalId, ProposalUpdate proposalUpdate) {
        Proposals proposal = findProposal(proposalId);

        proposal.setProposalValue(proposalUpdate.getProposalValue());
        proposal.setDescription(proposalUpdate.getDescription());
        proposal.setDeadline_date(proposalUpdate.getDeadline_date());

        return this.proposalRepository.save(proposal);
    }

    @Override
    public List<Proposals> searchAllProposals() {
        List<Proposals> proposals = this.proposalRepository.findAll();

        if (proposals.isEmpty()) {
            throw new DataAccessException("Lista de proposta vazia", HttpStatus.NO_CONTENT);
        }
        return proposals;
    }

    @Override
    public Proposals findProposal(Integer proposalId) {

        Proposals proposals = findProposalById(proposalId);

        if (proposals.getIsAccepted()){
            throw new ProposalAcceptedException("Propostas aceitas não podem ser alteradas.", HttpStatus.BAD_REQUEST);
        }
        return proposals;
    }

    public Boolean refuse(Integer proposalId) {
        Proposals proposals = findProposalById(proposalId);
        proposals.setIsAccepted(false);
        proposals.setIsRefused(true);
        this.proposalRepository.save(proposals);
        return true;
    }

    private Users findUserById(Integer userId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataAccessException("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    private Orders findOrderById(Integer orderId) {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new DataAccessException("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        } else if (order.get().isAccepted()) {
            throw new DataAccessException("Pedido já foi aceito.", HttpStatus.BAD_REQUEST);
        }
        return order.get();

    }

    private Proposals findProposalById(Integer proposalId) {
        Optional<Proposals> opt = this.proposalRepository.findById(proposalId);
        if (opt.isEmpty()) {
            throw new DataAccessException("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        }
        return opt.get();

    }
}
