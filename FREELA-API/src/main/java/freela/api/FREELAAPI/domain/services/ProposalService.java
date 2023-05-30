package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.application.web.dtos.response.ProposalsResponse;
import freela.api.FREELAAPI.application.web.enums.ProposalStatus;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProposalService {
    ProposalsResponse create(Integer originUserId, ProposalRequest proposal, Integer orderId);
    List<ProposalsResponse> findProposalsByUser(Authentication authentication, ProposalStatus clause);
    List<ProposalsResponse> findProposalsByUserId(Integer id, ProposalStatus clause);
    Boolean delete(Integer proposalId);
    Boolean refuse(Integer proposalId);
    ProposalsResponse update(Integer proposalId, ProposalUpdate proposals);
    List<Proposals> searchAllProposals();
    List<Proposals> findAllProposalsByOrderId(Integer order);
    List<Proposals> findAllRefusedProposalsByOrderId(Integer order);
    Proposals findProposal(Integer proposalId);
}
