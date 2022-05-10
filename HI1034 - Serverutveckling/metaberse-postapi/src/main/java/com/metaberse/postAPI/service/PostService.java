package com.metaberse.postAPI.service;

import com.metaberse.postAPI.records.CreatePost;
import com.metaberse.postAPI.records.PostRecord;
import com.metaberse.postAPI.records.UserListRecord;
import com.metaberse.postAPI.repository.Post;
import com.metaberse.postAPI.repository.PostRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostRecord> findAll() throws BackendException {
        var posts = postRepository.findAll();
        if (posts.isEmpty())
            throw new BackendException("No posts to be found");
        var dtos = new ArrayList<PostRecord>();

        for (var post : posts) {
            dtos.add(new PostRecord(post));
        }
        return dtos;
    }

    public PostRecord findById(long id) throws BackendException {
        var post = postRepository.findById(id);
        if (post.isEmpty())
            throw new BackendException("No posts to be found");

        return new PostRecord(post.get());
    }

    public PostRecord create(CreatePost postDTO) throws BackendException {
        var dt = Timestamp.from(Instant.parse(postDTO.timestamp()));
        var result = postRepository.save(new Post(postDTO.content(), postDTO.owner(), dt, postDTO.type()));
        log.info("Created: " + result);
        return new PostRecord(result);
    }

    public List<PostRecord> getPostByID(long id) throws BackendException {
        var posts = postRepository.findAllByOwner(id);
        if (posts.isEmpty())
            throw new BackendException("No posts");
        var postRecords = new ArrayList<PostRecord>();

        for (var post : posts.get()) {
            postRecords.add(new PostRecord(post));
        }
        return postRecords;
    }

    public List<PostRecord> findMuliplieById(UserListRecord users) throws BackendException {
        var posts = new ArrayList<PostRecord>();
        for (var user : users.users()) {
            posts.addAll(getPostByID(user));
        }
        return posts;
    }
}
