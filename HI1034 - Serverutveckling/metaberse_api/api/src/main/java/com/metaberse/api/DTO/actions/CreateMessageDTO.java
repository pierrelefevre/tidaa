package com.metaberse.api.DTO.actions;

import java.time.LocalDateTime;

public record CreateMessageDTO(long sender, long receiver, String timestamp, String content, String type){

}
