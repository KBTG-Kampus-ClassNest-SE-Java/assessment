package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Column(name = "user_id")
    @Size(min = 10, max = 10)
    private Long userId;

    @OneToMany(mappedBy = "userTicket")
    private List<Lottery> lotteries;

    @JoinColumn(name = "lottery_number", referencedColumnName = "lottery_number")
    @Column (name = "lottery_number")
    private String ticket;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }
}
