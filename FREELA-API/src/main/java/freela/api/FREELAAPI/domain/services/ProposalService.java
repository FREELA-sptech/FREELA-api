package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.resourses.entities.Proposals;

import java.util.List;

public interface ProposalService {
    Proposals create(Integer originUserId, ProposalRequest proposal, Integer orderId);
    List<Proposals> findProposalsByUser(Integer userId,String clause);
    Boolean delete(Integer proposalId);
    Boolean refuse(Integer proposalId);
    Proposals update(Integer proposalId, ProposalUpdate proposals);
    List<Proposals> searchAllProposals();
    Proposals findProposal(Integer proposalId);
}
