package com.metaberse.chatAPI.DTO;

import java.sql.Timestamp;

public record CreateMessage(long chatId, long senderId, String content, String type, Timestamp timestamp,
        String token) {
}
