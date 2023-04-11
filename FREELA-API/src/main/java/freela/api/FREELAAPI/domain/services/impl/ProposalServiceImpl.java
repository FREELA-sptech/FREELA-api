package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProposalServiceImpl implements ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Proposals create(Integer originUserId, Proposals proposal) {
        try {
            Optional<Users> user = usersRepository.findById(originUserId);
            proposal.setOriginUser(user.get());
            return proposalRepository.save(proposal);
        } catch (RuntimeException ex){
            throw new RuntimeException("Erro ao cadastrar proposta" + proposal);
        }
    }
}
