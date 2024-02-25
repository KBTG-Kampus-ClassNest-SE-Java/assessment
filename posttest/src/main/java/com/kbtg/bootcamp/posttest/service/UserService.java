package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.model.User;
import com.kbtg.bootcamp.posttest.model.UserTicket;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.response.UserTicketResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public void mockupUser(){
        List<User> users = new ArrayList<>();
        User user1 = new User("0000000001","User1","password");
        User user2 = new User("0000000002","User2","password");
        User user3 = new User("0000000003","User3","password");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        userRepository.saveAll(users);
    }





}
