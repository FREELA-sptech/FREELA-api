package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.application.web.dtos.response.ProposalsResponse;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.exceptions.ProposalAcceptedException;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.ProposalsMapper;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import freela.api.FREELAAPI.application.web.enums.ProposalStatus;

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
    public ProposalsResponse create(Integer originUserId, ProposalRequest proposal, Integer orderId) {
        try {
            Users user = findUserById(originUserId);
            Orders orders = findOrderById(orderId);
            Proposals proposals = proposalRepository.save(
                    new Proposals(
                            proposal.getProposalValue(),
                            user,
                            proposal.getDescription(),
                            proposal.getExpirationTime(),
                            orders.getId(),
                            false,
                            false));
            return ProposalsMapper.response(proposals);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao cadastrar proposta" + proposal);
        }
    }

    public List<ProposalsResponse> findProposalsByUser(Authentication authentication, ProposalStatus clause) {
        Users user = findUserById(TokenDetailsDto.getUserId(authentication));

        List<ProposalsResponse> response = new ArrayList<>();
        List<Proposals> proposals = new ArrayList<>();

        switch (clause) {
            case ALL:
                proposals = proposalRepository.findAllByOriginUser(user);
                break;
            case REFUSED:
                proposals = proposalRepository.findAllByOriginUserAndIsRefusedTrue(user);
                break;
            default:
                proposals = proposalRepository.findAllByOriginUserAndIsAcceptedTrue(user);
        }

        for (Proposals proposals1 : proposals) {
            response.add(ProposalsMapper.response(proposals1));
        }

        return response;
    }

    public Boolean delete(Integer id) {
        this.proposalRepository.delete(findProposalById(id));
        return true;
    }

    public ProposalsResponse update(Integer proposalId, ProposalUpdate proposalUpdate) {
        Proposals proposal = findProposal(proposalId);

        proposal.setProposalValue(proposalUpdate.getProposalValue());
        proposal.setDescription(proposalUpdate.getDescription());
        proposal.setExpirationTime(proposalUpdate.getExpirationTime());

        return ProposalsMapper.response(this.proposalRepository.save(proposal));
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
            throw new DataAccessException("Proposta não encontrado.", HttpStatus.NOT_FOUND);
        }
        return opt.get();

    }

    public List<Proposals> findAllProposalsByOrderId(Integer orderId) {

        return this.proposalRepository.findAllByDestinedOrder(orderId);
    }

    public List<Proposals> findAllRefusedProposalsByOrderId(Integer orderId) {

        return this.proposalRepository.findAllByDestinedOrderAndIsRefusedTrue(orderId);
    }
}
