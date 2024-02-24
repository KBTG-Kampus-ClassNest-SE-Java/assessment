package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @OneToMany(mappedBy = "userId")
    private List<UserTicket> tickets;

    @Id
    private String userId;


    public Users() {

    }

    public Users(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserTicket> getTickets() {
        return tickets;
    }
}
