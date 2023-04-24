package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import org.springframework.stereotype.Service;

public interface ProposalService {
    Proposals create(Integer originUserId, ProposalRequest proposal, Integer orderId);
}
