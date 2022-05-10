package com.metaberse.postAPI.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PostRepository postRepository) {
        return args -> {
            String lorum = "Donec finibus rhoncus nibh, ac semper enim. Aliquam mollis sagittis ante, at bibendum libero bibendum sed.";
            String ipsum = "Phasellus imperdiet consectetur convallis. Nullam rutrum auctor arcu in placerat. Morbi nec luctus metus. Sed placerat risus a sem molestie venenatis. Quisque et tempus nunc.";
            Timestamp dt = Timestamp.from(Instant.now());
            postRepository.save(new Post(ipsum, 4L, dt, "TEXT"));
            postRepository.save(new Post(lorum, 4L, extendTime(dt, 2L), "TEXT"));
            postRepository.save(new Post(lorum, 3L, extendTime(dt, 3L), "TEXT"));
            postRepository.save(new Post(ipsum, 3L, extendTime(dt, 4L), "TEXT"));
            postRepository.save(new Post(lorum, 2L, extendTime(dt, 5L), "TEXT"));
            postRepository.save(new Post(ipsum, 2L, extendTime(dt, 6L), "TEXT"));
            postRepository.save(new Post(lorum, 1L, extendTime(dt, 7L), "TEXT"));
            postRepository.save(new Post(ipsum, 1L, extendTime(dt, 8L), "TEXT"));
            postRepository.save(new Post(ipsum, 5L, extendTime(dt, 9L), "TEXT"));
            postRepository.save(new Post(lorum, 5L, extendTime(dt, 10L), "TEXT"));
        };
    }

    private Timestamp extendTime(Timestamp dt, long add) {
        dt.setTime(dt.getTime() + add);
        return dt;
    }
}
