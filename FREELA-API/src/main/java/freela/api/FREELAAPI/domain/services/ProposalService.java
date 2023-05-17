package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProposalService {
    Proposals create(Integer originUserId, ProposalRequest proposal, Integer orderId);
    List<Proposals> findProposalsByUser(Integer userId,String clause);
    Boolean delete(Proposals proposals);
}
