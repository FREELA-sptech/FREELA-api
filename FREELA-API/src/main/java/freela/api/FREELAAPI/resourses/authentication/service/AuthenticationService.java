package freela.api.FREELAAPI.resourses.authentication.service;

import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.resourses.authentication.dtos.UserDetailsDto;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOpt = usersRepository.findByEmail(username);

        if (userOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }
        return new UserDetailsDto(userOpt.get());
    }
}
