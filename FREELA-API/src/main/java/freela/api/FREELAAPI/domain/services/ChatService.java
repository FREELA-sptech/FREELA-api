package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.ChatRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.response.ChatResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderCreatedResponse;
import freela.api.FREELAAPI.application.web.dtos.response.ProposalsResponse;
import freela.api.FREELAAPI.application.web.enums.ProposalStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChatService {
    ChatResponse create(ChatRequest chatRequest);
    List<ChatResponse> getAllByUser(Authentication authentication);
}
