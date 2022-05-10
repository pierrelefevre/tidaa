package com.metaberse.api.DTO;

import com.metaberse.api.model.Post;

import java.sql.Timestamp;
import java.util.Objects;

public class PostDTO {

    private Long id;
    private Timestamp timestamp;
    private String content;
    private UserDTO owner;

    public PostDTO(Long id, Timestamp timestamp, String content, UserDTO owner) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
        this.owner = owner;
    }

    public PostDTO() {
    }

    public PostDTO(Timestamp timestamp, String content, UserDTO owner) {
        this.timestamp = timestamp;
        this.content = content;
        this.owner = owner;
    }

    public PostDTO(Post post){
        this.id = post.getId();
        this.timestamp =post.getTimestamp();
        this.content = post.getContent();
        this.owner = new UserDTO(post.getOwner());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(id, postDTO.id) && Objects.equals(timestamp, postDTO.timestamp) && Objects.equals(content, postDTO.content) && Objects.equals(owner, postDTO.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, content, owner);
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                ", owner=" + owner +
                '}';
    }
}
