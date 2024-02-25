package com.kbtg.bootcamp.posttest.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[0-9]{6}$")
    private String ticket;

    @Digits( integer = 10, fraction = 0)
    @Min(value = 0)
    private Long amount;

    @Min(value = 0)
    private Double price;

    @OneToMany(mappedBy = "lottery")
    private List<UserTicket> user_tickets;

    public Lottery() {
    }

    public Lottery(String ticket, Long amount, Double price) {
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

    public Long getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
