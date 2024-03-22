package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.user.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name= "lottery")
public class LotteryModel {

    @Id
    @Column(name = "ticket_id", nullable = false, length = 6)
    private String ticket;

    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "amount", nullable = false)
    private int amount;

    @OneToMany(mappedBy = "ticketId")
    private List<UserModel> userLotteries;

    public LotteryModel(){

    }

    public LotteryModel(String ticket, double price, int amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<UserModel> getUserLotteries() {
        return userLotteries;
    }

    public void setUserLotteries(List<UserModel> userLotteries) {
        this.userLotteries = userLotteries;
    }
}
