package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Lottery> lotteries = new ArrayList<>();

    private static int userCount = 0;

    public User(){

    }
    public User(String name) {
        this.id = Long.valueOf(String.format("%010d", userCount++));
        this.name = name;
    }

}
