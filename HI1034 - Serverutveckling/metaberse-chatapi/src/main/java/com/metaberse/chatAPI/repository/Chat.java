package com.metaberse.chatAPI.repository;

import com.metaberse.chatAPI.DTO.MessageRecord;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chatName", nullable = false)
    private String chatName;

    @OneToMany(mappedBy = "chat", orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @ElementCollection
    @Column(name = "user")
    @CollectionTable(name = "chat_users", joinColumns = @JoinColumn(name = "owner_id"))
    private Set<Long> users = new LinkedHashSet<>();

    public List<Message> getMessages() {
        return messages;
    }

    public List<MessageRecord> getMessageRecords(){
        List<MessageRecord> records = new ArrayList<>();
        for(var message : messages){
            records.add(new MessageRecord(message));
        }
        return records;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Long> getUsers() {
        return users;
    }

    public void setUsers(Set<Long> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Chat(String chatName, Set<Long> users) {
        this.chatName = chatName;
        this.users = users;
    }

    public Chat(Long id, String chatName, Set<Long> users) {
        this.id = id;
        this.chatName = chatName;
        this.users = users;
    }

    public Chat() {
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
