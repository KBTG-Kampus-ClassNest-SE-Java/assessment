package com.kbtg.bootcamp.posttest.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "user_Id", insertable = false, updatable = false)
    private String userid;
    private String ticket;
    private int price;
    private int amount;


    public UserTicketEntity() {
    }

    public UserTicketEntity(int id, String userid, String ticket, int price, int amount) {
        this.id = id;
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
