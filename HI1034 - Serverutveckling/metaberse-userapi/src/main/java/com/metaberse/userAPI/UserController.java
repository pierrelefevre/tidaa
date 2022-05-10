package com.metaberse.userAPI;

import com.metaberse.userAPI.DTO.*;
import com.metaberse.userAPI.repository.Token;
import com.metaberse.userAPI.service.BackendException;
import com.metaberse.userAPI.service.TokenService;
import com.metaberse.userAPI.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserRecord> users = new ArrayList<>(userService.findAll());
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
            if (checkIfNumber(username)) {
                return new ResponseEntity<>(userService.findByID(Long.parseLong(username)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody CreateUser user) {
        try {
            UserRecord _user = userService
                    .createUser(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") long id, @RequestBody UserRecord user) {
        try {
            return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {
        try {
            var userRecord = userService.login(loginForm);
            var tokenRecord = new TokenRecord(tokenService.generateToken(userRecord.id()));
            var res = new UserTokenRecord(userRecord, tokenRecord);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestBody TokenRecord tokenRecord) {
        try {
            log.warn(tokenRecord.timestamp());
            var instant = Instant.parse(tokenRecord.timestamp());
            var token = new Token(UUID.fromString(tokenRecord.tokenid()), Timestamp.from(instant),
                    tokenRecord.userid());
            tokenService.validateToken(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody TokenRecord tokenRecord) {
        try {
            log.warn(tokenRecord.timestamp());
            var instant = Instant.parse(tokenRecord.timestamp());
            var token = new Token(UUID.fromString(tokenRecord.tokenid()), Timestamp.from(instant),
                    tokenRecord.userid());
            var refreshToken = new TokenRecord(tokenService.refreshToken(token));
            return new ResponseEntity<>(refreshToken, HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/tokens")
    public ResponseEntity<Object> getAllTokens() {
        try {
            return new ResponseEntity<>(tokenService.getAllTokens(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<Object> getFriends(@PathVariable long id) {
        try {
            return new ResponseEntity<>(userService.getFriends(id), HttpStatus.OK);
        } catch (BackendException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
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

    @PostMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> addFriend(@PathVariable("userId") long userID,
            @PathVariable("friendId") long friendId) {
        try {
            return ResponseEntity.ok().body(userService.addFriend(userID, friendId));
        } catch (BackendException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean checkIfNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
