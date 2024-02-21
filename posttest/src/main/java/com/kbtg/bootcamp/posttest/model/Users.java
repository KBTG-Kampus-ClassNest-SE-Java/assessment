package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String username;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    List<UserTicket> userTickets;

}
