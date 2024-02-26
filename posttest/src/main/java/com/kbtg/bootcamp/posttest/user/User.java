package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter
@Entity(name = "users")
@Data
public class User {

    @NotNull
    @Id
    private Long user_id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    @Size(min = 3, max = 20, message = "user name should be between 3 and 20 character")
    private String name;

    public User() {
    }

    public User(String name) {
        this.user_id = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        this.name = name;
    }

}
