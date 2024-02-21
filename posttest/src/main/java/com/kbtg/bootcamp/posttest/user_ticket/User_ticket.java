package com.kbtg.bootcamp.posttest.user_ticket;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import lombok.ToString;

@Entity
@Table(name = "user_ticket")
public class User_ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]{10}$")
    private String userid;

    @NotNull
    private String transaction_type;

    @NotNull
    private Integer transaction_amount;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @ToString.Exclude
    private Lottery lottery;

    public User_ticket() {}

    public User_ticket( String userId, String transaction_type, Integer transaction_amount, Lottery lottery) {
        this.userid = userId;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.lottery = lottery;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userid;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public Integer getTransaction_amount() {
        return transaction_amount;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userid = userId;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public void setTransaction_amount(Integer transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }
}
