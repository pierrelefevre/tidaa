package com.metaberse.api.service;

import com.metaberse.api.DTO.PostDTO;
import com.metaberse.api.DTO.actions.CreatePostDTO;
import com.metaberse.api.model.Post;
import com.metaberse.api.repository.LoadDatabase;
import com.metaberse.api.repository.PostRepository;
import com.metaberse.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> findAll() throws BackendException {
        var posts = postRepository.findAll();
        if (posts.isEmpty())
            throw new BackendException("No posts to be found");
        var dtos = new ArrayList<PostDTO>();

        for (var post : posts) {
            dtos.add(new PostDTO(post));
        }
        return dtos;
    }


    public PostDTO create(CreatePostDTO postDTO) throws BackendException {
        var user = userRepository.findById(postDTO.ownerId());
        if (user.isEmpty())
            throw new BackendException("No such user found");
        var dt = Timestamp.from(Instant.parse( postDTO.timestamp() ));
        var result = postRepository.save(new Post(postDTO.content(), user.get(), dt));
        log.info("Created: " + result);
        return new PostDTO(result);
    }

    public PostDTO findById(long id) throws BackendException {
        var post = postRepository.findById(id);
        if (post.isEmpty())
            throw new BackendException("No posts to be found");

        return new PostDTO(post.get());
    }
}
