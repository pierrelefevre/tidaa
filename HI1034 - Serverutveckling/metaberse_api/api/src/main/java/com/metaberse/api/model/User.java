package com.metaberse.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "image")
    private String imageURL;

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    private List<Message> receivedMessages;

    @OrderBy("timestamp ASC")
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name = "user_users",
            joinColumns = @JoinColumn(name = "user_1_null", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_2_id", referencedColumnName = "id"))
    private Set<User> friends;

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public User() {
    }

    public User(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String image) {
        this.imageURL = image;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "firstname = " + firstname + ", " +
                "lastname = " + lastname + ", " +
                "image = " + imageURL + ")";
    }
}
