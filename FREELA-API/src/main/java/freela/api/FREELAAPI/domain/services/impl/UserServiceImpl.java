package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Users register(UserRequest userRequest) {
        return usersRepository.save(
                new Users(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        userRequest.getPassword(),
                        userRequest.getUserName()
                )
        );
    }

    @Override
    public Users login(String email, String senha) {
        return usersRepository.findByEmailAndPassword(email, senha);
    }
}
