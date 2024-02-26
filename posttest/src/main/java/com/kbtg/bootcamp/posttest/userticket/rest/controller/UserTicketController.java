package com.kbtg.bootcamp.posttest.userticket.rest.controller;

import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketReqDto;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserTicketController {
    private UserTicketService userTicketService;

    @Autowired
    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    //EXP03 Buy lottery
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketReqDto> buyLottery(
            @PathVariable("userId")
            @Pattern(regexp = "^\\d{10}$", message = "Must be a number only")
            @Size(min = 10, max = 10, message = "Must be a 10 digit")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "^\\d{6}$", message = "Must be a number only")
            @Size(min = 6, max = 6, message = "Must be a 6 digit")
            String ticketId) {
        return new ResponseEntity<>(userTicketService.buyLottery(userId, ticketId), HttpStatus.CREATED);
    }

    //EXP04 view user lottery
    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserTicketResDto> getLotteryByUserId(
            @PathVariable("userId")
            @Pattern(regexp = "^\\d{10}$", message = "Must be a number only")
            @Size(min = 10, max = 10, message = "Must be a 10 digit")
            String userId) {
        return new ResponseEntity<>(userTicketService.getLotteryByUserId(userId), HttpStatus.OK);
    }


    //EXP05 Sell lottery
    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<LotteryResponseDto> sellLottery(
            @PathVariable("userId")
            @Pattern(regexp = "^\\d{10}$", message = "Must be a number only")
            @Size(min = 10, max = 10, message = "Must be a 10 digit")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "^\\d{6}$", message = "Must be a number only")
            @Size(min = 6, max = 6, message = "Must be a 6 digit")
            String ticketId) {
        return new ResponseEntity<>(userTicketService.sellLottery(userId, ticketId), HttpStatus.OK);
    }

}