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
    public ResponseEntity<?> getAllUserLotteryByUserIdPage(
            @PathVariable(name = "requestedUserId") String requestedUserId
    ) {
        return
        lotteryService.getUserLotteryDetail(requestedUserId);
    }

    @GetMapping("/lotteries")
    public UserResponse getLotteriesPage() {
        List<String> collect = lotteryService.getAllLotteries().stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());
        return new UserResponse(collect);
    }

    @PostMapping("/{requestedUserId}/lotteries/{requestedTicketId}")
    public ResponseEntity<?> getBuyLotteryPage(
            @PathVariable(name = "requestedUserId") String requestedUserId,
            @PathVariable(name = "requestedTicketId") String requestedTicketId,
            @RequestBody UserRequest request
    ) {
        return lotteryService.buyLotteries(request);
    }

    @DeleteMapping("/{requestedUserID}/lotteries/{requestedTicketId}")
    public ResponseEntity<?> sellingBackALotteryPage(
            @PathVariable(name = "requestedUserID") String requestedUserID,
            @PathVariable(name = "requestedTicketId") String requestedTicketId
    ) {
        return
                lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID, requestedTicketId);
    }






}
