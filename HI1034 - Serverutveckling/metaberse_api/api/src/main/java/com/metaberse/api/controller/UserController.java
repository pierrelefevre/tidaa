package com.metaberse.api.controller;

import com.metaberse.api.DTO.UserDTO;
import com.metaberse.api.DTO.actions.LoginFormDTO;
import com.metaberse.api.repository.LoadDatabase;
import com.metaberse.api.service.BackendException;
import com.metaberse.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserDTO> users = new ArrayList<>(userService.findAll());
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/search")
    public ResponseEntity<Object> searchUser(@RequestParam String query) {
        try {
            return ResponseEntity.ok().body(userService.searchFor(query));
        } catch (BackendException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Object> getUserByName(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) {
        try {
            UserDTO _user = userService
                    .createUser(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") long id, @RequestBody UserDTO user) {
        try {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{username}/posts")
    public ResponseEntity<Object> getPostsByUser(@PathVariable String username) {
        try {
            return new ResponseEntity<>(userService.findPostsByUser(username), HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginFormDTO loginForm) {
        try {
            return new ResponseEntity<>(userService.login(loginForm.username(), loginForm.password()), HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }


    @GetMapping("/users/{userId}/messages")
    public ResponseEntity<Object> getUserMessages(@PathVariable("userId") long uId) {
        try {
            return new ResponseEntity<>(userService.findMessagesByUser(uId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/users/{userId}/messages/{partId}")
    public ResponseEntity<Object> getChat(@PathVariable("userId") long uId, @PathVariable("partId") long pId) {
        try {
            return new ResponseEntity<>(userService.findMessagesByConversation(uId, pId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/users/{userId}/conversations")
    public ResponseEntity<Object> getConversations(@PathVariable("userId") long uId) {
        try {
            return new ResponseEntity<>(userService.findConversations(uId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> addFriend(@PathVariable("userId") long userID, @PathVariable("friendId") long friendId) {
        try {
            return ResponseEntity.ok().body(userService.addFriend(userID, friendId));
        } catch (BackendException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> removeFriend(@PathVariable("userId") long uId, @PathVariable("friendId") long fId) {
        try {
            return ResponseEntity.ok().body(userService.removeFriend(uId, fId));
        } catch (BackendException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/users/{userID}/friends")
    public ResponseEntity<Object> getFriends(@PathVariable("userID") long userID){
                try {
                    return ResponseEntity.ok().body(userService.findFriendsByID(userID));
                }catch (BackendException e){
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
    }
    @GetMapping("/users/{userID}/friends/posts")
    public ResponseEntity<Object> getFriendsPost(@PathVariable("userID")long userID){
        try {
            return ResponseEntity.ok().body(userService.getFriendsPost(userID));
        }catch (BackendException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
