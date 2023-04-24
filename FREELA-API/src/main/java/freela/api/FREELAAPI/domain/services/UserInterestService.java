package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInterestService {
    void createUserInterest(List<Integer> subCategories, Users user);
}
