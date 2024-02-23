package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.user.UserTicket;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "lottery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lottery {

    // Main entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_id")
    private Integer lotteryId;

    @Column (name = "lottery_number")
    @Size(min = 6, max = 6)
    private String ticket;

    private Integer price;

    private Integer amount;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private UserTicket userTicket;

}
