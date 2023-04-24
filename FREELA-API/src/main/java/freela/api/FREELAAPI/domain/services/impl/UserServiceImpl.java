package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.UserInterest;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserInterestService userInterestService;

    @Override
    public Users register(UserRequest userRequest) {

        Users user = usersRepository.save(
                new Users(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        userRequest.getPassword(),
                        userRequest.getUserName()
                )
        );

        this.userInterestService.createUserInterest(userRequest.getSubCategoryId(),user);

        return user;
    }

    @Override
    public Users login(String email, String senha) {
        return usersRepository.findByEmailAndPassword(email, senha);
    }
}
