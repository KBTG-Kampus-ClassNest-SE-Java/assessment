package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="users")
public class Users {

    @Id
    @Size(min = 10, max = 10, message = "user_id show contain 10 characters of digit numbers")
    @Column(name="user_id")
    private String id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToOne(mappedBy = "users")
    private UserRole userRole;

    @OneToMany(mappedBy = "users")
    private List<UserPermission> userPermission;

    @OneToMany(mappedBy = "users")
    private List<UserTicket> userTickets;

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
