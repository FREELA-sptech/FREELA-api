package freela.api.FREELAAPI.domain.repositories;

import freela.api.FREELAAPI.resourses.entities.Chat;
import freela.api.FREELAAPI.resourses.entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages,Integer> {
    List<Messages> findAllByChat(Chat chat);
}
