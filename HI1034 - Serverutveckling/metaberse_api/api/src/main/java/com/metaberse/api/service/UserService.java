package com.metaberse.api.service;

import com.metaberse.api.DTO.MessageDTO;
import com.metaberse.api.DTO.PostDTO;
import com.metaberse.api.DTO.UserDTO;
import com.metaberse.api.model.Message;
import com.metaberse.api.model.User;
import com.metaberse.api.repository.LoadDatabase;
import com.metaberse.api.repository.MessageRepository;
import com.metaberse.api.repository.PostRepository;
import com.metaberse.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    final private UserRepository userRepository;
    final private PostRepository postRepository;
    final private MessageRepository messageRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository, PostRepository postRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.messageRepository = messageRepository;
    }

    public UserDTO createUser(UserDTO newUser) throws BackendException {
        try {//Todo: är detta tillräckligt?
            System.err.println("Create user: " + newUser.toString());
            var result = userRepository
                    .save(new User(
                            newUser.getUsername(),
                            newUser.getPassword(),
                            newUser.getFirstname(),
                            newUser.getLastname())
                    );
            return new UserDTO(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new BackendException("A user with that name already existed");
        }
    }

    public List<UserDTO> findAll() {
        var users = userRepository.findAll();
        var dtos = new ArrayList<UserDTO>();

        for (var user :
                users) {
            dtos.add(new UserDTO(user));
        }
        return dtos;
    }

    public UserDTO findById(long id) {

        return new UserDTO(userRepository.findById(id).get());
    }

    public UserDTO login(String username, String password) throws BackendException {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new BackendException("User does not exist");
        }
        if (!userRepository.existsByUsernameAndPassword(username, password)) {
            throw new BackendException("Wrong password");
        }
        var user = userRepository.findByUsername(username).get();

        return new UserDTO(user);
    }

    public List<PostDTO> findPostsByUser(String username) throws BackendException {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new BackendException("No user by this name");

        var posts = postRepository.findAllByOwner(user.get());
        var result = new ArrayList<PostDTO>();
        for (var post : posts) {
            result.add(new PostDTO(post));
        }
        return result;
    }

    public List<MessageDTO> findMessagesByUser(long uId) throws BackendException {
        var user = userRepository.findById(uId);

        if (user.isEmpty())
            throw new BackendException("No user by this id: " + uId);

        var messagesFromUser = messageRepository.findAllBySender(user.get());
        var messagesToUser = messageRepository.findAllByReceiver(user.get());

        var result = new ArrayList<Message>();
        result.addAll(messagesFromUser);
        result.addAll(messagesToUser);

        var resultDTO = new ArrayList<MessageDTO>();

        for (var message : result) {
            resultDTO.add(new MessageDTO(message));
        }

        return resultDTO;
    }

    public UserDTO findByUsername(String username) throws BackendException {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new BackendException("A user with that name does not exist " + username);
        return new UserDTO(user.get());
    }

    public List<MessageDTO> findMessagesByConversation(long uId, long pId) throws BackendException {
        var sender = userRepository.findById(uId);
        var receiver = userRepository.findById(pId);

        if (sender.isEmpty())
            throw new BackendException("A user with that id not found: " + uId);
        if (receiver.isEmpty())
            throw new BackendException("A user with that id not found: " + pId);
        //TODO: error name!

        var conversation = messageRepository.findAllBySenderAndReceiver(sender.get(), receiver.get());
        conversation.addAll(messageRepository.findAllBySenderAndReceiver(receiver.get(), sender.get()));
        var resultDTO = new ArrayList<MessageDTO>();

        for (var message : conversation) {
            resultDTO.add(new MessageDTO(message));
        }

        return resultDTO;
    }

    public List<UserDTO> findConversations(long uId) throws BackendException {
        var user = userRepository.findById(uId);

        if (user.isEmpty())
            throw new BackendException("No user by this id: " + uId);

        var messages = user.get().getReceivedMessages();
        messages.addAll(user.get().getSentMessages());

        var uniqueRelations = new HashSet<UserDTO>();

        for (var message : messages) {
            if (message.getReceiver().getId() != uId)
                uniqueRelations.add(new UserDTO(message.getReceiver()));

            if (message.getSender().getId() != uId)
                uniqueRelations.add(new UserDTO(message.getSender()));
        }

        return uniqueRelations.stream().toList();
    }

    public boolean addFriend(long userID, long friendId) throws BackendException {
        if(userID == friendId)
            return false;
        var userOP = userRepository.findById(userID);
        var friendOP = userRepository.findById(friendId);
        if (userOP.isEmpty()) {
            throw new BackendException("A user with that id not found: " + userID);
        }
        if (friendOP.isEmpty())
            throw new BackendException("A user with that id not found: " + friendId);
        var user = userOP.get();
        var friend = friendOP.get();

        user.getFriends().add(friend);
        userRepository.save(user);
        return true;
    }

    public List<UserDTO> searchFor(String query) throws BackendException {
        var userOP = userRepository.findAllByUsernameContaining(query);
        if (userOP.isEmpty())
            return new ArrayList<>();
        var users = new ArrayList<UserDTO>();
        for (var user : userOP.get()) {
            users.add(new UserDTO(user));
        }
        log.info(users.toString());
        return users;
    }

    public boolean removeFriend(long userID, long friendID) throws BackendException {
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
        return found;
    }

    public List<UserDTO> findFriendsByID(long userID) throws BackendException {
        var userOP = userRepository.findById(userID);
        if (userOP.isEmpty()) {
            throw new BackendException("A user with that id not found: " + userID);
        }
        var dtos = new ArrayList<UserDTO>();
        for (var friend : userOP.get().getFriends()) {
            dtos.add(new UserDTO(friend));
        }
        return dtos;
    }

    public List<PostDTO> getFriendsPost(long userID)throws BackendException {
        var userOP = userRepository.findById(userID);
        if (userOP.isEmpty()) {
            throw new BackendException("A user with that id not found: " + userID);
        }
        var posts = new ArrayList<PostDTO>();
        var user = userOP.get();
        for (var post :
                postRepository.findAllByOwner(user)) {
            posts.add(new PostDTO(post));
        }
        for (var friend : user.getFriends()) {
            for(var post : postRepository.findAllByOwner(friend)){
                posts.add(new PostDTO(post));
            }
        }
        return posts;
    }
}
