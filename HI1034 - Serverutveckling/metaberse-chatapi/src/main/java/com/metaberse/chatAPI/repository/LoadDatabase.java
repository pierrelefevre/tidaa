package com.metaberse.chatAPI.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
/*
    @Bean
    CommandLineRunner initDatabase(ChatRepository chatRepository, MessageRepository messageRepository) {
        return args -> {
            List<Long> list = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L);
            chatRepository.save(new Chat("The big groupchat", new HashSet<Long>(list)));
            chatRepository.save(new Chat("The small groupchat", new HashSet<Long>(list)));

            var chats = chatRepository.findAll();

            messageRepository.save(new Message(1L, "Hello i am 1", "TEXT", Timestamp.from(Instant.now()), chats.get(0)));
            messageRepository.save(new Message(2L, "Hello i am 2", "TEXT", Timestamp.from(Instant.now()), chats.get(0)));

            messageRepository.save(new Message(1L, "Hello i am 1", "TEXT", Timestamp.from(Instant.now()), chats.get(1)));
            messageRepository.save(new Message(2L, "Hello i am 2", "TEXT", Timestamp.from(Instant.now()), chats.get(1)));

        };
    }
    
 */
}
