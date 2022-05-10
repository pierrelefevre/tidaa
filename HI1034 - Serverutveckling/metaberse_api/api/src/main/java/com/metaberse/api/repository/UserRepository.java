package com.metaberse.api.repository;

import com.metaberse.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Optional<List<User>> findAllByUsernameContaining(String username);
}
