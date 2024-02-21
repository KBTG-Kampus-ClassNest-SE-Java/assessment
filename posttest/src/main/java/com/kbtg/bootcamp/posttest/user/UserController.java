package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/{requestedUserId}/lotteries")
    public Object getAllUserLotteryByUserIdPage(
            @PathVariable(name = "requestedUserId") Integer requestedUserId
    ) {
        List<Lottery> allLotteries = lotteryService.getAllLotteries();

        return ResponseEntity.ok().body(allLotteries);
    }

    @GetMapping("/lotteries")
    public UserResponse getLotteriesPage() {
        List<String> collect = lotteryService.getAllLotteries().stream()
                .map(lottery -> lottery.getTicket())
                .collect(Collectors.toList());
        return new UserResponse(collect);
    }

    @PostMapping("/{requestedUserId}/lotteries/{requestedTicketId}")
    public ResponseEntity getBuyLotteryPage(
            @PathVariable(name = "requestedUserId") Integer requestedUserId,
            @PathVariable(name = "requestedTicketId") Integer requestedTicketId,
            @RequestBody UserRequest request
    ) {
        return lotteryService.buyLotteries(request);
    }




}
