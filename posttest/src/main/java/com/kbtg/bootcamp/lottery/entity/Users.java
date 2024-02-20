package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @OneToMany(mappedBy = "userId")
    private List<UserTicket> tickets;

    @Id
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public List<UserTicket> getTickets() {
        return tickets;
    }
}
