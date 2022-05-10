package com.metaberse.chatAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Chat> findByChatId(long id);
}
