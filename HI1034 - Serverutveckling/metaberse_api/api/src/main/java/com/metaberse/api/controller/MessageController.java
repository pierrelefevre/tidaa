package com.metaberse.api.controller;

import com.metaberse.api.DTO.actions.CreateMessageDTO;
import com.metaberse.api.DTO.MessageDTO;
import com.metaberse.api.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
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
    public ResponseEntity<Object> createMessage(@RequestBody CreateMessageDTO message) {
        try {
            MessageDTO _message = messageService.create(message);
            return new ResponseEntity<>(_message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable long id){
        return new ResponseEntity<>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
