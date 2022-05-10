package com.metaberse.api.DTO;

import com.metaberse.api.model.Message;

import java.sql.Timestamp;
import java.util.Objects;

public class MessageDTO {
    private long id;
    private Timestamp timestamp;
    private String type;
    private String content;
    private UserDTO receiver;
    private UserDTO sender;

    public MessageDTO(long id, Timestamp timestamp, String type, String content, UserDTO receiver, UserDTO sender) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
    }

    public MessageDTO(Message message){
        this.id = message.getId();
        this.timestamp = message.getTimestamp();
        this.type = message.getType().toString();
        this.content = message.getContent();
        this.receiver = new UserDTO(message.getReceiver());
        this.sender = new UserDTO(message.getSender());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDTO receiver) {
        this.receiver = receiver;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", receiver=" + receiver +
                ", sender=" + sender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return id == that.id && Objects.equals(timestamp, that.timestamp) && Objects.equals(type, that.type) && Objects.equals(content, that.content) && Objects.equals(receiver, that.receiver) && Objects.equals(sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, type, content, receiver, sender);
    }
}
