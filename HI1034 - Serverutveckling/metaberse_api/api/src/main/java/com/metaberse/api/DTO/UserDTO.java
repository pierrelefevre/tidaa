package com.metaberse.api.DTO;

import com.metaberse.api.model.Message;
import com.metaberse.api.model.User;

import java.util.List;
import java.util.Objects;

public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String imageURL;

    public UserDTO(long id, String username, String password, String firstname, String lastname, String imageURL) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.imageURL = imageURL;
    }

    /**
     * Create a UserDTO from a Model user, WITHOUT Sent and received.
     * @param user
     */
    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.imageURL = user.getImageURL();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password) && Objects.equals(firstname, userDTO.firstname) && Objects.equals(lastname, userDTO.lastname) && Objects.equals(imageURL, userDTO.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, imageURL);
    }
}
