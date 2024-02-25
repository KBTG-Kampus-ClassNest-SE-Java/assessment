package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.Service.UserTicketService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<Map<String, String>> BuyTicket(@PathVariable @NotBlank @NotNull @Pattern(regexp = "\\d{10}") String userId, @PathVariable @NotBlank @NotNull @Pattern(regexp = "\\d{6}") String ticketId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", userticketService.BuyTicket(userId, ticketId)));
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<UserTicketResponseDto> getUserTicket(@PathVariable @NotBlank @NotNull @Pattern(regexp = "\\d{10}") String userId) {

        return ResponseEntity.ok(userticketService.getUserTicket(userId));
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable @NotBlank @NotNull @Pattern(regexp = "\\d{10}") String userId, @PathVariable @NotBlank @NotNull @Pattern(regexp = "\\d{6}") String ticketId) {

        return ResponseEntity.ok
                (Collections.singletonMap("ticket", userticketService.deleteLottery(userId, ticketId)));
    }
}
