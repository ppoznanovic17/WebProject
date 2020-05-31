package project.user.dao;

import project.user.User;

import java.io.Serializable;

public class UserDao implements Serializable {

    private String username;
    private String role;

    public UserDao() {

    }

    public UserDao(User u) {
        username = u.getUsername();
        role = u.getRole();
    }

    public UserDao(String username, String role) {
        this.role = role;
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
