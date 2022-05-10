package com.metaberse.postAPI.records;

public record CreatePost(long owner, String content, String type, String timestamp, String token) {
}
