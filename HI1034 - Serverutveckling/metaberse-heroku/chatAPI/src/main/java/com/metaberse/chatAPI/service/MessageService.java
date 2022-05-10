package com.metaberse.chatAPI.service;

import com.metaberse.chatAPI.DTO.ChatRecord;
import com.metaberse.chatAPI.DTO.CreateMessage;
import com.metaberse.chatAPI.DTO.MessageRecord;
import com.metaberse.chatAPI.repository.ChatRepository;
import com.metaberse.chatAPI.repository.Message;
import com.metaberse.chatAPI.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    private static final Logger log = LoggerFactory.getLogger(com.metaberse.chatAPI.service.ChatService.class);

    public MessageService(ChatRepository chatRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    public List<MessageRecord> findAll() {
        var messagesOP = messageRepository.findAll();
        var messages = new ArrayList<MessageRecord>();
        for (var message :
                messagesOP) {
            messages.add(new MessageRecord(message));
        }
        return messages;
    }

    public MessageRecord createMessage(CreateMessage message) throws BackendException {
        var chatOP = chatRepository.findById(message.chatId());
        if(chatOP.isEmpty())
            throw new BackendException("No such chat");
        var chat = chatOP.get();
        var timestamp = message.timestamp();
        if(timestamp == null)
            timestamp = Timestamp.from(Instant.now());
        var result = messageRepository.save(new Message(
                message.senderId(),
                message.content(),
                message.type(),
                timestamp,
                chat
        ));
        log.info("Message created: " + result);
        return new MessageRecord(result);
    }

}
