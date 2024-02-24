package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResponse> buyLottery(
            @PathVariable(value = "userId") String userId,
            @PathVariable(value = "ticketId") String ticketId
    ) {
        UserTicket userTicket = this.lotteryService.buyLottery(userId, ticketId);

        BuyLotteryResponse response = new BuyLotteryResponse(userTicket.getId().toString());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<GetLotteriesByUserIdResponse> getLotteriesByUserId(@PathVariable(value = "userId") String userId) {
        List<String> tickets = List.of("123456", "654321");
        GetLotteriesByUserIdResponse response = new GetLotteriesByUserIdResponse(tickets, 2, 160);
        return ResponseEntity.ok().body(response);
    }


    @ExceptionHandler(value = {LotteryNotFoundException.class, LotterySoldOutException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.notFound().build();
    }
}
