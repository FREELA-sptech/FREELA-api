package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.exceptions.ProposalAcceptedException;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProposalServiceImplTest {
    @Mock
    private ProposalRepository proposalRepository;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private OrderRepository orderRepository;

    @Test
    void testFindProposal_Success() {
        // Mock dos dados de teste
        Integer proposalId = 1;
        Proposals proposal = new Proposals();
        proposal.setId(proposalId);
        proposal.setIsAccepted(false);

        when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));

        ProposalServiceImpl proposalService = new ProposalServiceImpl(proposalRepository, usersRepository, orderRepository);

        Proposals result = proposalService.findProposal(proposalId);

        assertNotNull(result);
        assertEquals(proposal, result);
    }

    @Test
    void testFindProposal_Failure() {
        Integer proposalId = 1;
        Proposals proposal = new Proposals();
        proposal.setId(proposalId);
        proposal.setIsAccepted(true);

        when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));

        ProposalServiceImpl proposalService = new ProposalServiceImpl(proposalRepository, usersRepository, orderRepository);

        ProposalAcceptedException exception = assertThrows(ProposalAcceptedException.class, () -> {
            proposalService.findProposal(proposalId);
        });

        assertEquals("Propostas aceitas não podem ser alteradas.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}