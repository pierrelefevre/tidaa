package com.metaberse.userAPI.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("jacdaw", "password", "Jacob", "Lundin"));
            userRepository.save(new User("Ghaast", "password", "Oscar", "Ekenlöw"));
            userRepository.save(new User("Suppression", "password", "Karl", "Janrik"));
            userRepository.save(new User("UnsuitableCat", "password", "Pierre", "Le Fevre"));
            userRepository.save(new User("_Emil", "password", "Emil", "Karlsson"));
            userRepository.save(new User("Rackaracka", "password", "Mats Hjalmar", "Murman"));

            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));

            var users = userRepository.findAll();
            Random rand = new Random();
            for (int i = 0; i < users.size(); i++) {
                var friends = new ArrayList<User>();
                for (int j = 0; j < users.size(); j++) {
                    if(j!=i && rand.nextInt(10)%2 == 1){
                        friends.add(users.get(j));
                    }
                }
                users.get(i).setFriends(new HashSet<User>(friends ));
            }
            userRepository.saveAll(users);
            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));
        };
    }
}
