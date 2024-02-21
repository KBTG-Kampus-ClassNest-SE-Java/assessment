package com.kbtg.bootcamp.posttest.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", length = 10, unique = true, nullable = false)
    private String userId;

    @Column(name = "total_spent")
    private int totalSpent;

    @Column(name = "total_lottery")
    private int totalLottery;
}
