package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.response.*;
import freela.api.FREELAAPI.resourses.entities.*;

import java.util.ArrayList;
import java.util.List;

public class ChatMapper {
    public static ChatResponse response(Chat chat) {
        OrderChatResponse orderChatResponse =
                OrderChatResponse.builder()
                        .id(chat.getOrder().getId())
                        .title(chat.getOrder().getTitle())
                .build();

        return ChatResponse.builder()
                .id(chat.getId())
                .freelancerUser(UsuarioMapper.userOrderResponse(chat.getFreelancerUser()))
                .clientUser(UsuarioMapper.userOrderResponse(chat.getClientUser()))
                .order(orderChatResponse)
                .lastUpdate(chat.getLastUpdate())
                .build();
    }
}
