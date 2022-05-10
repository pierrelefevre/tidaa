package com.metaberse.chatAPI;

import com.metaberse.chatAPI.DTO.ChatRecord;
import com.metaberse.chatAPI.DTO.CreateChat;
import com.metaberse.chatAPI.DTO.CreateMessage;
import com.metaberse.chatAPI.service.ChatService;
import com.metaberse.chatAPI.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            ChatRecord _chat = chatService.createChat(chat);
            return new ResponseEntity<>(_chat, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<Object> getChatById(@PathVariable("chatId") Long chatId) {
        try {
            ChatRecord _chat = chatService.findById(chatId);
            return new ResponseEntity<>(_chat, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chat/owner/{id}")
    public ResponseEntity<Object> getChatsByOwner(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(chatService.findChats(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/messages")
    public ResponseEntity<Object> getAllMessages() {
        try {
            var messages = messageService.findAll();
            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody CreateMessage message) {
        try {

            return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
