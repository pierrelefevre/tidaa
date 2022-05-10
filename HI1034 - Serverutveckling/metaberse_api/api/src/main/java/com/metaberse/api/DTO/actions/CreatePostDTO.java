package com.metaberse.api.DTO.actions;


public record CreatePostDTO(long ownerId, String content, String timestamp) {
}
