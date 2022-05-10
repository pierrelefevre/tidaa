package com.metaberse.api.service;

import com.metaberse.api.DTO.actions.CreateMessageDTO;
import com.metaberse.api.DTO.MessageDTO;

import com.metaberse.api.model.Message;
import com.metaberse.api.repository.LoadDatabase;
import com.metaberse.api.repository.MessageRepository;
import com.metaberse.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    public MessageService(MessageRepository messageRepository,UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> findAll() throws BackendException {
        var messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            throw new BackendException("No messages");
        }
        var dtos = new ArrayList<MessageDTO>();
        for (var message : messages) {
            dtos.add(new MessageDTO(message));
        }
        return dtos;
    }

    public MessageDTO findById(long id) throws BackendException {
        var message = messageRepository.findById(id);
        if (message.isEmpty()) {
            throw new BackendException("No message with id " + id);
        }
        return new MessageDTO(message.get());
    }

    public MessageDTO create(CreateMessageDTO newMessage) throws BackendException{
        var sender = userRepository.findById(newMessage.sender());
        var receiver = userRepository.findById(newMessage.receiver());

        if(newMessage.content().isEmpty())
            throw new BackendException("No message sent");



        if (sender.isEmpty())
            throw new BackendException("A user with that id not found: " + newMessage.sender());
        if (receiver.isEmpty())
            throw new BackendException("A user with that id not found: " + newMessage.receiver());

        var message = new Message(Timestamp.from(Instant.parse(newMessage.timestamp() )),
                Message.Type.valueOf(newMessage.type()),
                newMessage.content(),
                receiver.get(),
                sender.get());

        log.info("Created: " + message);
        return new MessageDTO( messageRepository.save(message));

    }
}
