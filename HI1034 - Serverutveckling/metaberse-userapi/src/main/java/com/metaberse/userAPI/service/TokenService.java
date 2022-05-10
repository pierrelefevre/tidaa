package com.metaberse.userAPI.service;

import com.metaberse.userAPI.repository.Token;
import com.metaberse.userAPI.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token refreshToken(Token token) throws BackendException {
        var result = tokenRepository.findAllByUserid(token.getUserid());

        if (result.isEmpty()) {
            throw new BackendException("User " + token.getUserid() + " has no tokens");
        }
        Token foundToken = null;
        for (var resToken : result) {
            if (resToken.getTokenid().toString().equals(token.getTokenid().toString()))
                foundToken = resToken;
        }

        if (foundToken == null) {
            throw new BackendException(
                    "Token does not match: " + token.getTokenid().toString() + " for user " + token.getUserid());
        }

        if (foundToken.getTimestamp().toLocalDateTime()
                .compareTo(Timestamp.from(Instant.now()).toLocalDateTime().minusHours(3)) < 0) {
            throw new BackendException(
                    "Token expired: " + token.getTokenid().toString() + " for user " + token.getUserid());
        }

        var refreshToken = generateToken(token.getUserid());

        return refreshToken;
    }

    public void validateToken(Token token) throws BackendException {
        var result = tokenRepository.findAllByUserid(token.getUserid());

        if (result.isEmpty()) {
            throw new BackendException("User " + token.getUserid() + " has no tokens");
        }
        Token foundToken = null;
        for (var resToken : result) {
            if (resToken.getTokenid().toString().equals(token.getTokenid().toString()))
                foundToken = resToken;
        }

        if (foundToken == null) {
            throw new BackendException(
                    "Token does not match: " + token.getTokenid().toString() + " for user " + token.getUserid());
        }

        if (foundToken.getTimestamp().toLocalDateTime()
                .compareTo(Timestamp.from(Instant.now()).toLocalDateTime().minusHours(3)) < 0) {
            throw new BackendException(
                    "Token expired: " + token.getTokenid().toString() + " for user " + token.getUserid());
        }
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Token generateToken(Long userId) {
        Timestamp now = Timestamp.from(Instant.now());
        var newToken = new Token(UUID.randomUUID(), now, userId);
        tokenRepository.save(newToken);
        return newToken;
    }
}
