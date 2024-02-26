package com.kbtg.bootcamp.posttest.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kbtg.bootcamp.posttest.core.entity.AuditEntity;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_ticket")
@Data
@NoArgsConstructor
public class UserTicket extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "lottery_id")
    @JsonIgnore
    private Lottery lottery;

    public UserTicket(User user, Lottery lottery) {
        this.user = user;
        this.lottery = lottery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }
}
