package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioLoginDto;
import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioTokenDto;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByEmailAndPassword(String email, String senha);
    Optional<Users> findByEmail(String username);
    UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto);
}
