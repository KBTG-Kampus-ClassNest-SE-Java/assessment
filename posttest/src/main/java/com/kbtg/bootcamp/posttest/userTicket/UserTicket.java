package com.kbtg.bootcamp.posttest.userTicket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lottery_id")
    private  Long lotteryId;


    public UserTicket() {
    }

    public UserTicket(Long userId, Long lotteryId) {
        this.userId = userId;
        this.lotteryId = lotteryId;
    }


    public Long getLotteryId() {
        return lotteryId;
    }
}

