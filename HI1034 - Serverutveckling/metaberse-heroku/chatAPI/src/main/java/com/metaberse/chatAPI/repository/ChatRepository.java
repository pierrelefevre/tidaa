package com.metaberse.chatAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Set<Chat> findChatByUsersContaining(long id);
}
