package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.resourses.entities.Proposals;

public interface ProposalService {
    Proposals create(Integer originUserId, Proposals proposal);
}
