package com.kbtg.bootcamp.posttest.user.model;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lottery_id", referencedColumnName = "id", nullable = false)
    private LotteryTicket lottery;
}
