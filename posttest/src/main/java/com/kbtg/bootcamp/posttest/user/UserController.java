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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getALlUser(){
        return userService.getAllUser();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return (User) userService.getUserById(id);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "User Created")
    public User createUser(@Validated @RequestBody UserRequest request) {
        return userService.createUser(request);
    }



}
