package com.metaberse.chatAPI.DTO;

import java.util.Set;

public record CreateChat (String chatName, Set<Long> users){
}
