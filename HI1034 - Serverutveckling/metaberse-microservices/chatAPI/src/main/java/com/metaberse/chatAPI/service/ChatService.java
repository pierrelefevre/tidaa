package com.metaberse.chatAPI.service;

import com.metaberse.chatAPI.DTO.ChatRecord;
import com.metaberse.chatAPI.DTO.CreateChat;
import com.metaberse.chatAPI.repository.Chat;
import com.metaberse.chatAPI.repository.ChatRepository;
import com.metaberse.chatAPI.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    private static final Logger log = LoggerFactory.getLogger(com.metaberse.chatAPI.service.ChatService.class);

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    public ChatRecord createChat(CreateChat newChat) throws BackendException{
        try {
            log.info("creating chat");
            var result = chatRepository.save(new Chat(newChat.chatName(), newChat.users()));
            return new ChatRecord(result);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new BackendException("Error creating chat");
        }
    }
    
    public ChatRecord findById(long chatId) throws BackendException{
        try {
            log.info("finding " + chatId);
            var result = chatRepository.findById(chatId);
            if(result.isEmpty())
                throw new BackendException("There was no chat with id " + chatId);
            return new ChatRecord(result.get());
        }catch(Exception e){
            log.error(e.getMessage());
            throw new BackendException("Error finding chat");
        }
    }

    public List<ChatRecord> findChats(Long id) throws BackendException {
        var chats = chatRepository.findChatByUsersContaining(id);
        var result = new ArrayList<ChatRecord>();

        if(chats.isEmpty())
            throw new BackendException("There was no chat for user: " + id);
        for (var chat :
                chats) {
            result.add(new ChatRecord(chat));
        }
        return result;
    }
}
