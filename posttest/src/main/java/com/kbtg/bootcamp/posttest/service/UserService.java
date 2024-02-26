package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.model.User;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void mockupUser() {
        List<User> users = new ArrayList<>();
        User user1 = new User("0000000001", "User1", "password", "0888888888");
        User user2 = new User("0000000002", "User2", "password", "0999999999");
        User user3 = new User("0000000003", "User3", "password", "0111111111");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        userRepository.saveAll(users);
    }

}
