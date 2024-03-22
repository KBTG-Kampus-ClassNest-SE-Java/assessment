package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_ticket")
public class UserModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private LotteryModel ticketId;

    public UserModel(){

    }

    public UserModel(Integer id, String userId, LotteryModel ticketId) {
        this.id = id;
        this.userId = userId;
        this.ticketId = ticketId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LotteryModel getTicketId() {
        return ticketId;
    }

    public void setTicketId(LotteryModel ticketId) {
        this.ticketId = ticketId;
    }
}
