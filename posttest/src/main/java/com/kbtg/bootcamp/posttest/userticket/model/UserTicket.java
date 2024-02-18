package com.kbtg.bootcamp.posttest.userticket.model;

import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Lottery lottery;

}
