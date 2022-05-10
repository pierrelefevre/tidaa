package com.metaberse.api.model;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;

    public Post() {
        super();
    }

    public Post(String content, User owner) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.content = content;
        this.owner = owner;
    }

    public Post(String content, User owner, Timestamp timestamp){
        this.content = content;
        this.owner = owner;
        this.timestamp = timestamp;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "timestamp = " + timestamp + ", " +
                "content = " + content + ", " +
                "owner = " + owner + ")";
    }
}
