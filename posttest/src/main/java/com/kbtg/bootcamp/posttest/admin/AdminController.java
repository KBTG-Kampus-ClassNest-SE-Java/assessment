package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<Object> addLottery(@RequestBody LotteryRequest ticketRequest){
        this.lotteryService.addTicket(ticketRequest);
        Map<String, String> data = new HashMap<>();
        data.put("ticket", ticketRequest.ticket());
        return new ResponseEntity<>(data, HttpStatus.CREATED);

    }
}
