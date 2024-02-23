package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryListResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userLottery.UserLotteryRequest;
import com.kbtg.bootcamp.posttest.userLottery.UserLotteryResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LotteryService lotteryService;

    @Autowired
    public UserController(UserService userService, LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return (User) userService.getUserById(id);
    }

    @GetMapping("/lotteries/list")
    public LotteryListResponse getAllLotteryTickets() {
        List<Lottery> lotteries = lotteryService.getAllLotteries();
        List<String> listOfLottery = lotteries.stream()
                .map(Lottery::getIdAsString)
                .collect(Collectors.toList());
        return new LotteryListResponse(listOfLottery);
    }



    @GetMapping("/{userId}/lotteries")
    public UserLotteryResponse getUserLotteries(@PathVariable("userId") String userId) {
        return userService.showUserLotteriesList(userId);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "User Created")
    public User createUser(@Validated @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/lotteries/buy")
    public UserResponse buyLottery(@RequestBody UserLotteryRequest userLotteryRequest) {
        return userService.buyLottery(userLotteryRequest.userId(), userLotteryRequest.lotteryId());
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponse sellAllLotteries(@PathVariable String userId, @PathVariable String ticketId) {
        return userService.deleteLottery(userId, ticketId);
    }
}
