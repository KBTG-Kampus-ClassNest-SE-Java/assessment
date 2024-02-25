package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`user`")
public class User {

    @Id
    @Column(length = 10)
    private String ID;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    public User() {
    }

    public User(String ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
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
