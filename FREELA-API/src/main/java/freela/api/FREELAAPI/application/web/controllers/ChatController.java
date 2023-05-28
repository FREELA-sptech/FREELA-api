package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.ChatRequest;
import freela.api.FREELAAPI.application.web.dtos.response.ChatResponse;
import freela.api.FREELAAPI.application.web.dtos.response.MessageResponse;
import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.services.ChatService;
import freela.api.FREELAAPI.domain.services.MessageService;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;

    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestBody ChatRequest chatRequest){
        return ResponseEntity.ok(this.chatService.create(chatRequest));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllByUser(Authentication authentication){
        return ResponseEntity.ok(this.chatService.getAllByUser(authentication));
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<MessageResponse>> getAllByChat(@PathVariable @NotNull Integer chatId){
        return ResponseEntity.ok(this.messageService.getAllMessages(chatId));
    }
}
