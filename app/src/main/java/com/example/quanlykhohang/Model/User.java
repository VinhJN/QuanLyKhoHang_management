package com.example.quanlykhohang.Model;

public class User {
    private String user,password,fullname,email;
    private int role;

    public User() {
    }

    public User(String user, String password, String fullname, String email, int role) {
        this.user = user;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
