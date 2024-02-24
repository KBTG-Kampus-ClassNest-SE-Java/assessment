package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.Service.UserTicketService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserTicketService userticketService;

    public UserController(UserTicketService user_ticketService) {
        this.userticketService = user_ticketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> BuyTicket(@PathVariable @NotBlank @Pattern(regexp = "\\d{10}") String userId, @PathVariable @NotBlank @Pattern(regexp = "\\d{6}", message = "Value must be a 6-digit number") String ticketId) {
        String transactionId = userticketService.BuyTicket(userId, ticketId);
        Map<String, String> response = Collections.singletonMap("id", transactionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<UserTicketResponseDto> getUserTicket(@PathVariable @NotBlank @Pattern(regexp = "\\d{10}") String userId) {
        UserTicketResponseDto userTicket = userticketService.getUserTicket(userId);

        return ResponseEntity.ok(userTicket);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable @NotBlank @Pattern(regexp = "\\d{10}") String userId, @PathVariable @NotBlank @Pattern(regexp = "\\d{6}", message = "Value must be a 6-digit number") String ticketId) {
        userticketService.deleteLottery(userId, ticketId);
        Map<String, String> response = Collections.singletonMap("ticket", ticketId);

        return ResponseEntity.ok(response);
    }
}
