package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private final UserTicketService user_ticketService;

    public UserController(UserTicketService user_ticketService) {
        this.user_ticketService = user_ticketService;
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> BuyTicket(@PathVariable String userId, @PathVariable String ticketId) {
        String transactionId = user_ticketService.BuyTicket(userId, ticketId);
        Map<String, String> response = Collections.singletonMap("id", transactionId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserTicketResponseDto> getUserTicket(@PathVariable String userId) {
        UserTicketResponseDto userTicket = user_ticketService.getUserTicket(userId);

        return ResponseEntity.ok(userTicket);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable String userId, @PathVariable String ticketId) {
        String SoldTicket = user_ticketService.deleteLottery(userId, ticketId);
        Map<String, String> response = Collections.singletonMap("ticket", SoldTicket);

        return ResponseEntity.ok(response);
    }
}
