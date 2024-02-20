package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userLottery.UserLotteryRequest;
import com.kbtg.bootcamp.posttest.userLottery.UserLotteryResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
        return (User) userService.getUserById(id);
    }

    @PostMapping("/users")
    @ApiResponse(responseCode = "201", description = "User Created")
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

    @GetMapping("/users/{userId}/lotteries")
    public UserLotteryResponse getUserLotteries(@PathVariable("userId") String userId) {
        List<String> tickets = userService.getUserLotteryTickets(userId);
        int count = tickets.size();
        int cost = count * 80;

        return new UserLotteryResponse(tickets, count, cost);
    }

    @DeleteMapping("/users/{userId}/lotteries/sell")
    public User sellAllLotteries(@PathVariable String userId) {
        userService.deleteLotteries(userId);

        return (User) userService.getUserById(userId);
    }
}
