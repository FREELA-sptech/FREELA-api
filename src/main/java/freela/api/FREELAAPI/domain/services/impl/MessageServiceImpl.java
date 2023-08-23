package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.Exception.UserNotFoundException;
import freela.api.FREELAAPI.application.web.dtos.request.MessageRequest;
import freela.api.FREELAAPI.application.web.dtos.response.MessageResponse;
import freela.api.FREELAAPI.domain.repositories.ChatRepository;
import freela.api.FREELAAPI.domain.repositories.MessageRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.MessageService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.MessageMapper;
import freela.api.FREELAAPI.resourses.entities.Chat;
import freela.api.FREELAAPI.resourses.entities.Messages;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private ChatRepository chatRepository;
    private UsersRepository usersRepository;

    private MessageRepository messageRepository;

    public MessageServiceImpl(ChatRepository chatRepository, UsersRepository usersRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageResponse> getAllMessages(Integer chatId) {
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(
                () -> new UserNotFoundException("Chat não encontrado!")
        );

        List<Messages> response = this.messageRepository.findAllByChat(chat);

        List<MessageResponse> messages = new ArrayList<>();

        for (Messages res : response) {
            messages.add(MessageMapper.response(res));
        }

        return messages;
    }

    @Override
    public MessageResponse create(Integer userId, MessageRequest messageRequest) {
        Users user = this.usersRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        Chat chat = this.chatRepository.findById(messageRequest.getChatId()).orElseThrow(
                () -> new UserNotFoundException("Chat não encontrado!")
        );

        Messages message = new Messages(user, messageRequest.getMessage(), messageRequest.getTime(),chat);

        this.messageRepository.save(message);

        return MessageMapper.response(message);
    }
}
