package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
private String id;
private Integer price;
private Integer amount;

public Lottery(String ticket,Integer price, Integer amount) {
    this.id = ticket;
    this.price = price;
    this.amount = amount;
}
}
