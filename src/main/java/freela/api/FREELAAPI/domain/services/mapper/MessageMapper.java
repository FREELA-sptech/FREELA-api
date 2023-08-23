package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.response.ChatResponse;
import freela.api.FREELAAPI.application.web.dtos.response.MessageResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderChatResponse;
import freela.api.FREELAAPI.resourses.entities.Chat;
import freela.api.FREELAAPI.resourses.entities.Messages;

public class MessageMapper {
    public static MessageResponse response(Messages messages) {
        return MessageResponse.builder()
                .id(messages.getId())
                .from(UsuarioMapper.userOrderResponse(messages.getFrom()))
                .message(messages.getMessage())
                .chat(ChatMapper.response(messages.getChat()))
                .time(messages.getTime())
                .build();
    }
}
