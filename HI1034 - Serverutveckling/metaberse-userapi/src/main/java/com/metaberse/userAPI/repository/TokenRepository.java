package com.metaberse.userAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID>  {
    Optional<Token> findTokenByTokenid(UUID tokenid);
    Optional<Token> findTokenByUserid(Long userid);
    List<Token> findAllByUserid(Long userid);
}