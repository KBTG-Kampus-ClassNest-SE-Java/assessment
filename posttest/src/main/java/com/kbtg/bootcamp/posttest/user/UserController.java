package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userTicket.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.userTicket.UserTicketResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LotteryService lotteryService;


    @Autowired
    public UserController(UserService userService, LotteryService lotteryService, UserRepository userRepository) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public List<User> getALlUser(){
        return userService.getAllUser();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return (User) userService.getUserById(id);
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


    @PostMapping("/{userId}/lotteries/{ticketId}")
    public UserTicketResponse buyLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userService.buyLottery(userId,ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponse sellBackLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userService.deleteLottery(userId, ticketId);
    }
}
