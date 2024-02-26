package com.kbtg.bootcamp.posttest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerUserService implements UserService {
    @Autowired
    private UserRepository userRepository;
}
