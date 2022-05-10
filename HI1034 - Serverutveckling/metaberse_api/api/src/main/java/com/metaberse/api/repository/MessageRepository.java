package com.metaberse.api.repository;


import com.metaberse.api.model.Message;
import com.metaberse.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
    List<Message> findAllBySenderAndReceiver(User sender, User receiver);
}
