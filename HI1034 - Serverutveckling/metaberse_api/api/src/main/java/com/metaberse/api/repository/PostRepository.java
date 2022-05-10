package com.metaberse.api.repository;

import com.metaberse.api.model.Post;
import com.metaberse.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByOwner(User owner);
}
