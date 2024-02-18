package com.kbtg.bootcamp.posttest.userticket.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserTicketController {

    private UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketDto> buyLotteries(@PathVariable String userId, @PathVariable String ticketId) {
        return new ResponseEntity<>(userTicketService.buyLotteries(userId, ticketId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserTickerSummaryDto> getLotteriesByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(userTicketService.getLotteriesByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<TicketDto> deleteLotteriesByUserId(@PathVariable String userId, @PathVariable String ticketId) {
        return new ResponseEntity<>(userTicketService.deleteLotteriesByUserId(userId, ticketId), HttpStatus.OK);
    }
}
