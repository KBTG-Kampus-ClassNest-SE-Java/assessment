package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "lottery")
@Data
public class Lottery {

    @Id
    @JsonProperty("ticket")
    private Long lottery_id;
    private Integer price;
    private Integer amount;

    public Lottery() {
    }

    public Lottery(String ticket, Integer price, Integer amount) {
        this.lottery_id = Long.valueOf(ticket);
        this.price = price;
        this.amount = amount;
    }


    @JsonProperty("ticket")
    public String getIdAsString() {
        return String.valueOf(lottery_id);
    }

    @JsonIgnore
    public Long getId() {
        return lottery_id;
    }

}
