package com.metaberse.api.repository;

import com.metaberse.api.model.Message;
import com.metaberse.api.model.Post;
import com.metaberse.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.metaberse.api.model.Message.Type.TEXT;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PostRepository postRepository, MessageRepository messageRepository, UserRepository userRepository, ImageRepository imageRepository) {
        return args -> {
            var jacob = userRepository.save(new User("jacdaw","password","Jacob","Lundin"));
            var oscar = userRepository.save(new User("Ghaast", "password", "Oscar", "Ekenlöw"));
            var kalle = userRepository.save(new User("Suppression", "password", "Karl", "Janrik"));
            var pierre = userRepository.save(new User("UnsuitableCat", "password", "Pierre", "Le Fevre"));
            var emil = userRepository.save(new User("_Emil", "password", "Emil", "Karlsson"));
            var mats = userRepository.save(new User("Rackaracka","password","Mats Hjalmar", "Murman"));

            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));
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

            postRepository.findAll().forEach(post -> log.info("Preloaded " + post));

            var hello = "Hello, how are you doing?";
            var amGood = "Hey, I'm great. This is an awesome project";
            var reply = "That's right, we do some good coding";
            var next = "So what is next?";
            var time = Timestamp.valueOf(LocalDateTime.now());

            messageRepository.save(new Message(time,TEXT, hello, kalle, pierre));
            messageRepository.save(new Message(time,TEXT, amGood, pierre, kalle));
            messageRepository.save(new Message(time,TEXT, reply, kalle, pierre));
            messageRepository.save(new Message(time,TEXT, next, kalle, pierre));
            messageRepository.save(new Message(time,TEXT, hello, kalle, oscar));
            messageRepository.save(new Message(time,TEXT, hello, kalle, emil));
            messageRepository.save(new Message(time,TEXT, amGood, emil, kalle));

            messageRepository.findAll().forEach(message -> log.info("Preloaded " + message));
            
            
        };
    }
}

