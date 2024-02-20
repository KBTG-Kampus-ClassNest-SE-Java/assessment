package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryListResponse;
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

    @GetMapping("/users/lotteries/list")
    public LotteryListResponse getAllLotteries() {
        List<String> tickets = lotteryService.getAllLotteryTickets();
        return new LotteryListResponse(tickets);
    }



    @GetMapping("/users/{userId}/lotteries")
    public UserLotteryResponse getUserLotteries(@PathVariable("userId") String userId) {
        List<String> tickets = userService.getUserLotteryTickets(userId);
        int count = tickets.size();
        int cost = count * 80;

        return new UserLotteryResponse(tickets, count, cost);
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

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public List<Lottery> sellAllLotteries(@PathVariable String userId, @PathVariable String ticketId) {
        return userService.deleteLottery(userId, ticketId);
    }
}
