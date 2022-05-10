package com.metaberse.userAPI.DTO;

import com.metaberse.userAPI.repository.Token;

public record TokenRecord(String tokenid, String timestamp, Long userid) {
    public TokenRecord(Token token) {
        this(token.getTokenid().toString(), token.getTimestamp().toInstant().toString(), token.getUserid());

    }
}

/*
 * 
 * public record UserRecord (Long id, String username, String firstname, String
 * lastname, String imageUrl){
 * public UserRecord(User user){
 * this(user.getId(),user.getUsername(), user.getFirstname(),
 * user.getLastname(), user.getImageUrl());
 * }
 * }
 */