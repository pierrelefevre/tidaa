package com.metaberse.chatAPI.DTO;

import com.metaberse.chatAPI.repository.Chat;
import com.metaberse.chatAPI.repository.Message;

import java.sql.Timestamp;

public record MessageRecord (long id, long senderId, String content, String type, Timestamp timestamp, long chat){
    public MessageRecord(Message result) {
        this(result.getId(), result.getSenderId(), result.getContent(), result.getType(), result.getTimestamp(), result.getChat().getId());
    }
}
