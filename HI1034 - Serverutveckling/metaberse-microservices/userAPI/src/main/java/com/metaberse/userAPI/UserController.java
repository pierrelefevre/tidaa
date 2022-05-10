package com.metaberse.userAPI;

import com.metaberse.userAPI.DTO.CreateUser;
import com.metaberse.userAPI.DTO.LoginForm;
import com.metaberse.userAPI.DTO.UserRecord;
import com.metaberse.userAPI.repository.LoadDatabase;
import com.metaberse.userAPI.repository.User;
import com.metaberse.userAPI.repository.UserRepository;
import com.metaberse.userAPI.service.BackendException;
import com.metaberse.userAPI.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
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
                return  new ResponseEntity<>(userService.findByID(Long.parseLong(username)), HttpStatus.OK);
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
            return new ResponseEntity<>(userService.login(loginForm), HttpStatus.OK);
        } catch (BackendException e) {
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
    public ResponseEntity<Object> addFriend(@PathVariable("userId") long userID, @PathVariable("friendId") long friendId) {
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
