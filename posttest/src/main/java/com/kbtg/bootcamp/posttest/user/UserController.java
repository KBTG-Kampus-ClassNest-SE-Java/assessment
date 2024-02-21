package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/{requestedUserId}/lotteries")
    public ResponseEntity<Boolean> getAllUserLotteryByUserIdPage(
            @PathVariable(name = "requestedUserId") Integer requestedUserId
    ) {
        return
        lotteryService.getAllLotteriesByUserId(requestedUserId.toString());
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
