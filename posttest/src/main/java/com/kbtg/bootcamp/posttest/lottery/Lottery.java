package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.user.User;
import jakarta.persistence.*;


import java.util.List;


@Entity
@Table(name= "lottery")
public class Lottery {

    @Id
    @Column(name = "ticket_id", nullable = false, length = 6)
    private String ticket;

    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "ticketId")
    private List<User> userLotteries;

    public Lottery(String ticket, Integer price, Integer amount, List<User> userLotteries) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
        this.userLotteries = userLotteries;
    }

    public Lottery() {
    }

    public Lottery(String ticket) {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public List<User> getUserLotteries() {
        return userLotteries;
    }

    public void setUserLotteries(List<User> userLotteries) {
        this.userLotteries = userLotteries;
    }
}
