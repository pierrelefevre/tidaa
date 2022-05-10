package com.metaberse.api;

import com.metaberse.api.DTO.UserDTO;
import com.metaberse.api.model.Message;
import com.metaberse.api.model.Post;
import com.metaberse.api.model.User;
import com.metaberse.api.repository.*;
import com.metaberse.api.service.*;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.metaberse.api.model.Message.Type.TEXT;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApiApplicationTests {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @MockBean
    ImageService imageService;
    @MockBean
    MessageService messageService;
    @MockBean
    PostService postService;
    @MockBean
    UserService userService;

    @BeforeEach
    public void setUp() {
        var jacob = userRepository.save(new User("jacdaw", "password", "Jacob", "Lundin"));
        var oscar = userRepository.save(new User("Ghaast", "password", "Oscar", "Ekenlöw"));
        var kalle = userRepository.save(new User("Suppression", "password", "Karl", "Janrik"));
        var pierre = userRepository.save(new User("UnsuitableCat", "password", "Pierre", "Le Fevre"));
        var emil = userRepository.save(new User("_Emil", "password", "Emil", "Karlsson"));
        var mats = userRepository.save(new User("Rackaracka", "password", "Mats Hjalmar", "Murman"));

        System.out.println(jacob);
        String lorum = "Donec finibus rhoncus nibh, ac semper enim. Aliquam mollis sagittis ante, at bibendum libero bibendum sed.";
        String ipsum = "Phasellus imperdiet consectetur convallis. Nullam rutrum auctor arcu in placerat. Morbi nec luctus metus. Sed placerat risus a sem molestie venenatis. Quisque et tempus nunc.";

        postRepository.save(new Post(lorum, pierre));
        postRepository.save(new Post(ipsum, pierre));
        postRepository.save(new Post(lorum, kalle));
        postRepository.save(new Post(ipsum, kalle));
        postRepository.save(new Post(lorum, emil));
        postRepository.save(new Post(ipsum, emil));
        postRepository.save(new Post(lorum, oscar));
        postRepository.save(new Post(ipsum, oscar));
        postRepository.save(new Post(ipsum, mats));
        postRepository.save(new Post(lorum, mats));

        var hello = "Hello, how are you doing?";
        var amGood = "Hey, I'm great. This is an awesome project";
        var reply = "That's right, we do some good coding";
        var next = "So what is next?";
        var time = Timestamp.valueOf(LocalDateTime.now());

        messageRepository.save(new Message(time, TEXT, hello, kalle, pierre));
        messageRepository.save(new Message(time, TEXT, amGood, pierre, kalle));
        messageRepository.save(new Message(time, TEXT, reply, kalle, pierre));
        messageRepository.save(new Message(time, TEXT, next, kalle, pierre));
        messageRepository.save(new Message(time, TEXT, hello, kalle, oscar));
        messageRepository.save(new Message(time, TEXT, hello, kalle, emil));
        messageRepository.save(new Message(time, TEXT, amGood, emil, kalle));

    }

    @Test
    public void getPosts() throws BackendException {
        var posts = postRepository.findAll();
        AssertionsForClassTypes.assertThat(posts.size()).isGreaterThan(0);
    }

    @Test
    public void createPost() throws BackendException {
        var posts = postRepository.findAll();
        var jacob = userRepository.findByUsername("jacdaw").get();

        postRepository.save(new Post("hej",jacob ));

        var newPosts = postRepository.findAll();

        AssertionsForClassTypes.assertThat(newPosts.size()).isGreaterThan(posts.size());
    }

    @Test
    public void getMessages() throws BackendException {
        var posts = messageRepository.findAll();
        AssertionsForClassTypes.assertThat(posts.size()).isGreaterThan(0);
    }

    @Test
    public void createMessage() throws BackendException {
        var posts = messageRepository.findAll();
        var jacob = userRepository.findByUsername("jacdaw").get();
        var time = Timestamp.valueOf(LocalDateTime.now());
        messageRepository.save(new Message(time, TEXT,"hej",jacob, jacob ));

        var newPosts = messageRepository.findAll();

        AssertionsForClassTypes.assertThat(newPosts.size()).isGreaterThan(posts.size());
    }

    @Test
    public void findByUsername() throws BackendException {
        var jacob = userRepository.findByUsername("jacdaw").get();

        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("username", "jacdaw");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("firstname", "Jacob");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("lastname", "Lundin");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("password", "password");
    }

    @Test
    public void findById() {
        var oscirOP = userRepository.findById(72L);
        AssertionsForClassTypes.assertThat(oscirOP).isNotEmpty();
        var oscir = oscirOP.get();
        AssertionsForClassTypes.assertThat(oscir).hasFieldOrPropertyWithValue("username", "Ghaast");
        AssertionsForClassTypes.assertThat(oscir).hasFieldOrPropertyWithValue("firstname", "Oscar");
        AssertionsForClassTypes.assertThat(oscir).hasFieldOrPropertyWithValue("lastname", "Ekenlöw");
        AssertionsForClassTypes.assertThat(oscir).hasFieldOrPropertyWithValue("password", "password");
    }

    @Test
    public void remove() throws BackendException {
        var jacob = userRepository.findByUsername("jacdaw").get();

        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("username", "jacdaw");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("firstname", "Jacob");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("lastname", "Lundin");
        AssertionsForClassTypes.assertThat(jacob).hasFieldOrPropertyWithValue("password", "password");

        userRepository.delete(jacob);

        var jacobDeleted = userRepository.findByUsername("jacdaw");

        AssertionsForClassTypes.assertThat(jacobDeleted).isEmpty();
    }

    @Test
    void contextLoads() throws Exception {
        AssertionsForClassTypes.assertThat(imageRepository).isNotNull();
        AssertionsForClassTypes.assertThat(messageRepository).isNotNull();
        AssertionsForClassTypes.assertThat(postRepository).isNotNull();
        AssertionsForClassTypes.assertThat(userRepository).isNotNull();
    }

    @Test
    void findAll() throws Exception {
        var messages = messageRepository.findAll();
    }
}