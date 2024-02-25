package com.kbtg.bootcamp.posttest.entity;


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
    @JoinColumn(name = "ticket_id")
    private Lottery lottery;


    public UserTicket( Users users, Lottery lottery,int amount) {

        this.users = users;
        this.lottery = lottery;
        this.amount = amount;

    }


}
