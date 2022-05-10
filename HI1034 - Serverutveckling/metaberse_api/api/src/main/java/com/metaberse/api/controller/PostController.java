package com.metaberse.api.controller;

import com.metaberse.api.DTO.PostDTO;
import com.metaberse.api.DTO.actions.CreatePostDTO;
import com.metaberse.api.service.BackendException;
import com.metaberse.api.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getByID(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
        }catch(BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody CreatePostDTO createPostDTO){
        try {
            return new ResponseEntity<>(postService.create(createPostDTO), HttpStatus.CREATED);
        }catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }


}
