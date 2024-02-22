package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "users")
@Data
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private String id;
private String name;

@OneToMany(targetEntity = Lottery.class)
private List<Lottery> lotteries = new ArrayList<>();


private static int userCount = 0;


public User(String name) {
    this.id = String.format("%010d", userCount++);
    this.name = name;
}

}
