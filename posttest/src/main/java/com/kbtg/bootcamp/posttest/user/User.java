package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter
@Entity(name = "users")
@Data
public class User {

    @Id
    private Long user_id;

    private String name;

    public  User(){

    }
    public User(String name) {
        this.user_id = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        this.name = name;
    }

}
