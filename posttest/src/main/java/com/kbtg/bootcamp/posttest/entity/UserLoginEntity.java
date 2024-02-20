package com.kbtg.bootcamp.posttest.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_login")
public class UserLoginEntity {

    @Id
    private String User_Id;
    private String Name;


//    @OneToMany(mappedBy = "User_Id")
//    private List<UserTicketEntity> userTickets;


    public UserLoginEntity() {
    }

    public UserLoginEntity(String user_Id, String name) {
        User_Id = user_Id;
        Name = name;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}


