package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;

@Entity
@Table(name="user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_ticket_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name="lottery_id")
    private Lottery lottery;



}
