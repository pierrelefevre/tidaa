package com.metaberse.chatAPI;

import com.metaberse.chatAPI.DTO.ChatRecord;
import com.metaberse.chatAPI.DTO.CreateChat;
import com.metaberse.chatAPI.DTO.CreateMessage;
import com.metaberse.chatAPI.service.BackendException;
import com.metaberse.chatAPI.service.ChatService;
import com.metaberse.chatAPI.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.metaberse.chatAPI.requestHandler.RequestHandler;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Object> createChat(@RequestBody CreateChat chat) {
        try {
            if (!RequestHandler.validateToken(chat.token()))
                throw new BackendException("Invalid token");
            ChatRecord _chat = chatService.createChat(chat);
            return new ResponseEntity<>(_chat, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chat/owner/{chatId}")
    public ResponseEntity<Object> getChatsByOwner(@PathVariable Long chatId, @RequestParam String token) {
        try {
            if (!RequestHandler.validateToken(token))
                throw new BackendException("Invalid token");
            return new ResponseEntity<>(chatService.findChats(chatId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody CreateMessage message) {
        try {

            if (!RequestHandler.validateToken(message.token()))
                throw new BackendException("Invalid token");
            return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
