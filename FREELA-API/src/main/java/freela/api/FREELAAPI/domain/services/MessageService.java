package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.MessageRequest;
import freela.api.FREELAAPI.application.web.dtos.response.MessageResponse;
import freela.api.FREELAAPI.resourses.entities.Messages;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getAllMessages(Integer chatId);
    MessageResponse create(Integer userId, MessageRequest messageRequest);
}
