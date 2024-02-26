package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 6, max = 6)
    @Pattern(regexp = "\\d{6}", message = "Ticket must be  6 digits")
    private String ticket;

    private Double price;
    private Long amount;

    @Override
    public String toString() {
        return "Lottery{" +
                "ticket='" + ticket + '\'' +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "userId")
    private Profile profile;

    public Lottery() {}

    public Lottery(String ticket, Double price, Long amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public Lottery(String ticket, Double price, Long amount, Profile profile) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
        this.profile =profile;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public Integer getId() {
        return id;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
