package com.kbtg.bootcamp.posttest.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @Column(name = "user_id")
    private Integer id;
    private String username;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    List<UserTicket> userTickets;

}
