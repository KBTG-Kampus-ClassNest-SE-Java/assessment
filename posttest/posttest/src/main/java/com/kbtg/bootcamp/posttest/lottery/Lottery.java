package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.user_ticket.User_ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[0-9]{6}$")
    private String ticket;

    @NotNull
    private Integer amount;

    @NotNull
    private Float price;

    @OneToMany(mappedBy = "lottery")
    private List<User_ticket> user_tickets;

    public Lottery() {
    }

    public Lottery(String ticket, Integer amount, Float price) {
        this.ticket = ticket;
        this.amount = amount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTicket() {
        return ticket;
    }

    public Integer getAmount() {
        return amount;
    }

    public Float getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
