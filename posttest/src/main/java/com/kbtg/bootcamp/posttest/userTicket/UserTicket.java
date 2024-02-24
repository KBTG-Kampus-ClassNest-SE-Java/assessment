package com.kbtg.bootcamp.posttest.userTicket;

import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "ticket_id",length = 6, nullable = false)
    private String ticket_id;

    public UserTicket() {
    }

    public UserTicket(String user_id, String ticket_id) {
        this.user_id = user_id;
        this.ticket_id = ticket_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
