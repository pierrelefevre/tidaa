package com.metaberse.imageApi.records;

import java.sql.Timestamp;

public record CreateMessage(long chatId, long senderId, String content, String type, Timestamp timestamp){
}
