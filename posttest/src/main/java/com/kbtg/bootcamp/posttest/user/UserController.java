package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("")
    public List<Profile> helloProfile() {
        return
        lotteryService.getAllUserProfile();
    }

    @GetMapping("/lotteries")
    public UserResponseEXP02 getLotteriesPage() {
        List<String> collect = lotteryService.getAllLotteries().stream()
                .map(lottery -> lottery.getTicket())
                .collect(Collectors.toList());
        return new UserResponseEXP02(collect);
    }

    @PostMapping("/{requestedUserId}/lotteries/{requestedTicketId}")
    public String getBuyLotteryPage(
            @PathVariable(name = "requestedUserId") Integer requestedUserId,
            @PathVariable(name = "requestedTicketId") Integer requestedTicketId,
            @RequestBody UserRequest request
    ) {
        return "test";
    }




}
