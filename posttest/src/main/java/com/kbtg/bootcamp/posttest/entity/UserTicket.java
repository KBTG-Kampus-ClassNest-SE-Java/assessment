package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String user_id;
    private String ticket_id;


    public UserTicket(String user_id, String ticket_id) {
        this.user_id = user_id;
        this.ticket_id = ticket_id;
    }

    public UserTicket() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
