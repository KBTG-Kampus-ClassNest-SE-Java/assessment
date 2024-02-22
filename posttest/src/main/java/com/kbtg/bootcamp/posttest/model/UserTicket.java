package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_ticket")
@NoArgsConstructor
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ticket_id")
    private Integer id;


    private int amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lottery_id")
    private Lottery lottery;


    public UserTicket(int amount, Users users, Lottery lottery) {
        this.amount = amount;
        this.users = users;
        this.lottery = lottery;

    }


}
