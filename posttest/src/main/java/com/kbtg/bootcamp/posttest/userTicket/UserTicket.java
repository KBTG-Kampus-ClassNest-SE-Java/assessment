package com.kbtg.bootcamp.posttest.userTicket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    private String user_id;
    @ManyToOne
    @JoinColumn(name = "ticket")
    private Lottery lottery;
    private int price;

    public UserTicket(String userId, int pricePerQty, int amount) {
        this.user_id = userId;
        this.price = pricePerQty;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }
    public Integer getId() {
        return id;
    }
}
