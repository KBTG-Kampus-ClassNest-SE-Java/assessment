package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "User_Id", insertable = false, updatable = false)
    private String User_Id;
    private String ticket;
    private int price;
    private int amount;

//    @ManyToOne
//    @JoinColumn(name = "User_Id")
//    private UserLoginEntity userLoginEntity;

    public UserTicketEntity() {
    }

    public UserTicketEntity(int id, String user_Id, String ticket, int price, int amount) {
        this.id = id;
        User_Id = user_Id;
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
