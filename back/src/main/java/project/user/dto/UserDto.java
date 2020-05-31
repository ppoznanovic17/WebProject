package project.user.dto;


import project.user.User;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String username;
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(User u) {
        username = u.getUsername();
        password = u.getPassword();
    }

    public UserDto() {

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
}
