package com.kbtg.bootcamp.posttest.models.userTicket;

import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_ticket")
@Getter
@Setter
@NoArgsConstructor
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userId", length = 10, nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name="lottery_id", nullable=false)
    private Lottery lottery;

    public UserTicket(String userId, Lottery lottery) {
        this.userId = userId;
        this.lottery = lottery;
    }
}