package com.mycompany.cleaningcompany;

import java.io.Serializable;

public class User implements Serializable {

    private boolean isAdmin; // Indicates whether the user is an admin or not
    private String username; // The username of the user
    private String password; // The password of the user

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false; // By default, the user is not an admin
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
