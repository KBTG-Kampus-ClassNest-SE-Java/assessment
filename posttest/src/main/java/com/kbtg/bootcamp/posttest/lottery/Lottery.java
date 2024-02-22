package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbtg.bootcamp.posttest.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "lotteries")
@Data
public class Lottery {

    @Id
    @JsonProperty("ticket")
    private Long id;

    private Integer price;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public Lottery(String ticket, Integer price, Integer amount) {
        this.id = Long.valueOf(ticket);
        this.price = price;
        this.amount = amount;
    }
}
