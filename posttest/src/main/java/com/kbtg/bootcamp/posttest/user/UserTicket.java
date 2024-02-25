package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "lottery_id", referencedColumnName = "id")
    private Lottery lottery;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price")
    private double price;

}
