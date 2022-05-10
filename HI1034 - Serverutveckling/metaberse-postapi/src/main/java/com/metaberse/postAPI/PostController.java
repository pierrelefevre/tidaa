package com.metaberse.postAPI;

import com.metaberse.postAPI.records.CreatePost;
import com.metaberse.postAPI.records.UserListRecord;
import com.metaberse.postAPI.service.BackendException;
import com.metaberse.postAPI.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.metaberse.postAPI.requestHandler.RequestHandler;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello it's me MAAAARIO";
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/posts/owner")
    public ResponseEntity<Object> getPostsByUserList(@RequestBody UserListRecord users) {
        try {
            return new ResponseEntity<>(postService.findMuliplieById(users), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getByID(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody CreatePost createPost) {
        try {
            if (!RequestHandler.validateToken(createPost.token()))
                throw new BackendException("Invalid token");
            return new ResponseEntity<>(postService.create(createPost), HttpStatus.CREATED);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/posts/owner/{id}")
    public ResponseEntity<Object> getPostsByOwner(@PathVariable long id) {
        try {
            return new ResponseEntity<>(postService.getPostByID(id), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
