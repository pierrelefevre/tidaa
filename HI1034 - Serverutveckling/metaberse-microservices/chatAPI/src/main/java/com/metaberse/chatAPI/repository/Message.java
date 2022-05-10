package com.metaberse.chatAPI.repository;
import com.metaberse.chatAPI.DTO.ChatRecord;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message(Long senderId, String content, String type, Timestamp timestamp, Chat chat) {
        this.senderId = senderId;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
        this.chat = chat;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", chatID=" + chat.getId() +
                '}';
    }

    public ChatRecord getChatRecord() {
        return new ChatRecord(chat);
    }
}
