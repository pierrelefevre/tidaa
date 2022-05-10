package com.metaberse.userAPI.service;

import com.metaberse.userAPI.DTO.CreateUser;
import com.metaberse.userAPI.DTO.LoginForm;
import com.metaberse.userAPI.DTO.UserRecord;
import com.metaberse.userAPI.repository.User;
import com.metaberse.userAPI.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserRecord createUser(CreateUser newUser) throws BackendException {
        try {//Todo: är detta tillräckligt?
            log.info("Creating user");
            var result = userRepository
                    .save(new User(
                            newUser.username(),
                            newUser.password(),
                            newUser.firstname(),
                            newUser.lastname())
                    );
            log.info("Created: " + result);
            return new UserRecord(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BackendException("A user with that name already existed");
        }
    }

    public List<UserRecord> findAll() {
        var users = userRepository.findAll();
        var dtos = new ArrayList<UserRecord>();

        for (var user :
                users) {
            dtos.add(new UserRecord(user));
        }
        return dtos;
    }

    public UserRecord login(LoginForm loginForm) throws BackendException {
        if (userRepository.findByUsername(loginForm.username()).isEmpty()) {
            throw new BackendException("User does not exist");
        }
        if (!userRepository.existsByUsernameAndPassword(loginForm.username(), loginForm.password())) {
            throw new BackendException("Wrong password");
        }
        var user = userRepository.findByUsername(loginForm.username()).get();

        return new UserRecord(user);
    }

    public UserRecord findByUsername(String username) throws BackendException {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new BackendException("A user with that name does not exist " + username);
        return new UserRecord(user.get());
    }

    public List<UserRecord> searchFor(String query) throws BackendException {
        var userOP = userRepository.findAllByUsernameContaining(query);
        if (userOP.isEmpty())
            return new ArrayList<>();
        var users = new ArrayList<UserRecord>();
        for (var user : userOP.get()) {
            users.add(new UserRecord(user));
        }
        log.info(users.toString());
        return users;
    }

    public UserRecord updateUser(long id, UserRecord userRecord) throws BackendException {
        var userOp= userRepository.findById(id);
        if(userOp.isEmpty())
            throw new BackendException("No such user");
        var user = userOp.get();

        if(userRecord.username() != null){
            user.setUsername(userRecord.username());
        }
        if(userRecord.firstname()!=null){
            user.setFirstname(userRecord.firstname());
        }
        if(userRecord.lastname()!=null){
            user.setLastname(userRecord.lastname());
        }
        if(userRecord.imageUrl()!=null){
            user.setImageUrl(userRecord.imageUrl());
        }
        userRepository.save(user);
        return new UserRecord(user);
    }

    public List<UserRecord> getFriends(long id) throws BackendException {
        var userOp= userRepository.findById(id);
        if(userOp.isEmpty())
            throw new BackendException("No such user");
        var friends = userOp.get().getFriends().stream().toList();
        var result = new ArrayList<UserRecord>();
        for (var user :
                friends) {
            result.add(new UserRecord(user));
        }
        return result;
    }

    public boolean removeFriend(long userID, long friendID) throws BackendException {
        if(userID == friendID){
            log.info("Trying to remove same id");
            return false;
        }
        var userOP = userRepository.findById(userID);
        var friendOP = userRepository.findById(friendID);
        if (userOP.isEmpty()) {
            throw new BackendException("A user with that id not found: " + userID);
        }
        if (friendOP.isEmpty())
            throw new BackendException("A user with that id not found: " + friendID);
        var user = userOP.get();
        boolean found = user.getFriends().removeIf(x -> x.getId() == friendID);
        userRepository.save(user);
        log.info(user.getUsername() + " is no longer friend with " + friendOP.get().getUsername());
        return found;
    }


    public boolean addFriend(long userID, long friendID) throws BackendException {
        if(userID == friendID)
            return false;
        var userOP = userRepository.findById(userID);
        var friendOP = userRepository.findById(friendID);
        if (userOP.isEmpty()) {
            throw new BackendException("A user with that id not found: " + userID);
        }
        if (friendOP.isEmpty())
            throw new BackendException("A user with that id not found: " + friendID);
        var user = userOP.get();
        var friend = friendOP.get();

        if(user.getFriends().contains(friend)){
            log.info(user.getUsername() + " is already friend with " + friend.getUsername());
            return false;
        }
        user.getFriends().add(friend);
        userRepository.save(user);
        log.info(user.getUsername() + " is now friend with " + friend.getUsername());
        return true;
    }

    public UserRecord findByID(Long id) throws BackendException{
        var user = userRepository.findById(id);
        if (user.isEmpty())
            throw new BackendException("A user with that ID does not exist " + id);
        return new UserRecord(user.get());
    }
}
