package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.userLottery.UserLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {

    private final UserService userService;
    private final LotteryService lotteryService;

    @Autowired
    public UserController(UserService userService, LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String id) {
        // Implementation to retrieve user by ID
        return (User) userService.getUserById(id);
    }


    @PostMapping("/users")
    public User createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/users/lotteries/buy")
    public void buyLottery(@RequestBody UserLotteryRequest userLotteryRequest) {
        Lottery lottery = lotteryService.getLotteryById(userLotteryRequest.lotteryId());
        if (lottery == null) {
            throw new IllegalArgumentException("Lottery not found with ID: " + userLotteryRequest.lotteryId());
        }

        userService.buyLottery(userLotteryRequest.userId(), userLotteryRequest.lotteryId());
    }


    @DeleteMapping("/{userId}/lotteries/sell")
    public void sellAllLotteries(@PathVariable("userId") String userId) {

    }
}
