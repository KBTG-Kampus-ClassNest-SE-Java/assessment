package com.kbtg.bootcamp.posttest.userticket.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Slf4j
public class UserTicketController {

    private UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketDto> buyLotteries(
            @PathVariable("userId")
            @Pattern(regexp = "^0\\d{9}$", message = "must be a number only  and start with '0'")
            @Size(min = 10, max = 10, message = "must be a 10 digit")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "^\\d{6}$", message = "must be a number only")
            @Size(min = 6, max = 6, message = "must be a 6 digit")
            String ticketId) {
            return new ResponseEntity<>(userTicketService.buyLotteries(userId, ticketId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserTickerSummaryDto> getLotteriesByUserId(
            @PathVariable("userId")
            @Pattern(regexp = "^0\\d{9}$", message = "must be a number only  and start with '0'")
            @Size(min = 10, max = 10, message = "must be a 10 digit")
            String userId) {
        return new ResponseEntity<>(userTicketService.getLotteriesByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<TicketResponseDto> deleteLotteriesByUserId(
            @PathVariable("userId")
            @Pattern(regexp = "^0\\d{9}$", message = "must be a number only  and start with '0'")
            @Size(min = 10, max = 10, message = "must be a 10 digit")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "^\\d{6}$", message = "must be a number only")
            @Size(min = 6, max = 6, message = "must be a 6 digit")
            String ticketId) {
        return new ResponseEntity<>(userTicketService.deleteLotteriesByUserId(userId, ticketId), HttpStatus.OK);
    }
}
