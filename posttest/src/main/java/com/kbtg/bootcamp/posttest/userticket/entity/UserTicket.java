package com.kbtg.bootcamp.posttest.userticket.entity;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id",length = 10)
    @Size(min = 10, max = 10)
    @NotNull
    private String userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Lottery lottery;

    public UserTicket() {
    }

    public UserTicket(String userId, Lottery lottery) {
        this.userId = userId;
        this.lottery = lottery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }


}
