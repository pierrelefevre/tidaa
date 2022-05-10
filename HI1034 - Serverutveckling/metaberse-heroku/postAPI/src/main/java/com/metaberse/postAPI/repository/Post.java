package com.metaberse.postAPI.repository;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "owner", nullable = false)
    private long owner;

    @Column(name = "type", nullable = false)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Post(String content, long owner, Timestamp dt, String type) {
        this.content = content;
        this.owner = owner;
        this.timestamp=dt;
        this.type = type;

    }
    public Post(){
    }


    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return owner == post.owner && Objects.equals(id, post.id) && Objects.equals(timestamp, post.timestamp) && Objects.equals(content, post.content) && Objects.equals(type, post.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, content, owner, type);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                ", owner=" + owner +
                ", type='" + type + '\'' +
                '}';
    }
}
