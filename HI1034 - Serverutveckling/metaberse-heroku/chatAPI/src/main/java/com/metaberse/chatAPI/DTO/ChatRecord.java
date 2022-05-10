package com.metaberse.chatAPI.DTO;

import com.metaberse.chatAPI.repository.Chat;

import java.util.List;
import java.util.Set;

public record ChatRecord (long chatId, String chatName, Set<Long> users, List<MessageRecord> messages){
    public ChatRecord(Chat result) {
        this(result.getId(), result.getChatName(), result.getUsers(), result.getMessageRecords());
    }
}
