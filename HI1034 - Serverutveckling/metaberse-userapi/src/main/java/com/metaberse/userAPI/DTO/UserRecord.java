package com.metaberse.userAPI.DTO;

import com.metaberse.userAPI.repository.User;
import com.metaberse.userAPI.repository.UserRepository;

import java.util.Set;

public record UserRecord (Long id, String username, String firstname, String lastname, String imageUrl){
    public UserRecord(User user){
        this(user.getId(),user.getUsername(), user.getFirstname(), user.getLastname(), user.getImageUrl());
    }
}
