package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lottery")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lottery_number", length = 6)
    @Size(min = 6, max = 6)
    private String ticket;

    private Integer price;

    private Integer amount;

    public Lottery(Integer id, String ticket, Integer price, Integer amount) {
        this.id = id;
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public Lottery() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
