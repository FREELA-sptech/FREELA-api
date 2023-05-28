package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.Exception.UserNotFoundException;
import freela.api.FREELAAPI.application.web.dtos.request.ChatRequest;
import freela.api.FREELAAPI.application.web.dtos.response.ChatResponse;
import freela.api.FREELAAPI.domain.repositories.ChatRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.ChatService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.ChatMapper;
import freela.api.FREELAAPI.resourses.entities.Chat;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private ChatRepository chatRepository;
    private UsersRepository usersRepository;
    private OrderRepository orderRepository;

    public ChatServiceImpl(ChatRepository chatRepository, UsersRepository usersRepository, OrderRepository orderRepository) {
        this.chatRepository = chatRepository;
        this.usersRepository = usersRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ChatResponse create(ChatRequest chatRequest) {
        Users user = this.usersRepository.findById(chatRequest.getUserId()).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        Users freelancer = this.usersRepository.findById(chatRequest.getFreelancerId()).orElseThrow(
                () -> new UserNotFoundException("Freelancer não encontrado!")
        );

        Orders orders = this.orderRepository.findById(chatRequest.getOrderId()).orElseThrow(
                () -> new UserNotFoundException("Ordem não encontrada!")
        );

        Chat chat = new Chat(freelancer, user, orders, chatRequest.getLastUpdate());

        this.chatRepository.save(chat);

        return ChatMapper.response(chat);
    }

    @Override
    public List<ChatResponse> getAllByUser(Authentication authentication) {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        List<Chat> response = new ArrayList<>();

        if (user.getIsFreelancer()) {
            response = this.chatRepository.findAllByFreelancerUser(user);
        } else {
            response = this.chatRepository.findAllByClientUser(user);
        }

        List<ChatResponse> chats = new ArrayList<>();

        for (Chat chat : response) {
            chats.add(ChatMapper.response(chat));
        }

        return chats;
    }
}
