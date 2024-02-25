package com.kbtg.bootcamp.posttest.lottery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    private String userId;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<UserLottery> userLotteries;
}
