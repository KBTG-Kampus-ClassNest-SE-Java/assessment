package com.kbtg.bootcamp.posttest.lottery;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "lottery")
public class LotteryClass {

    @Id
    @Column(name = "ticket",length = 6)
    //Todo num 0-9
    private String ticket;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Integer price;

    public LotteryClass() {
    }

    public LotteryClass(String ticket, Integer amount, Integer price) {
        this.ticket = ticket;
        this.amount = amount;
        this.price = price;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
