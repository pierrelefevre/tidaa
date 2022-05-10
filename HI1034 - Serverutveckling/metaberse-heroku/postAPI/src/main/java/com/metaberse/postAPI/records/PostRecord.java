package com.metaberse.postAPI.records;

import com.metaberse.postAPI.repository.Post;

import java.sql.Timestamp;

public record PostRecord(long id, Timestamp timestamp, String type ,  String content, String owner ) {
    private static String url ="http://localhost:8081/users/";
    public PostRecord(Post post){
        this(post.getId(), post.getTimestamp(), post.getType(), post.getContent(), String.valueOf(post.getOwner()));
    }
}
