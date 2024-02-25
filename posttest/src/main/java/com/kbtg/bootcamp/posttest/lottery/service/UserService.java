package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.entity.User;

import java.util.Optional;

public interface UserService {

    public User addUser(User user);

    public Optional<User> findUserByUsername(String username);
}
