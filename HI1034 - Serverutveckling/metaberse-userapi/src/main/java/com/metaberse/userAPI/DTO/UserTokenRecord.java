package com.metaberse.userAPI.DTO;

import com.metaberse.userAPI.repository.Token;
import com.metaberse.userAPI.repository.User;
import com.metaberse.userAPI.repository.UserRepository;

import java.util.Set;

public record UserTokenRecord (UserRecord user, TokenRecord token){
    public UserTokenRecord(User user, Token token){
        this(new UserRecord(user), new TokenRecord(token));
    }
}
