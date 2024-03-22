package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kbtg.bootcamp.posttest.payload.LotteryDetailDto;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> createUserAndLottery(@PathVariable("userId")
                                                                    @NotBlank(message = "User ID value must not be blank")
                                                                    @Pattern(regexp = "\\d{10}", message = "User ID must be 10 digit number")
                                                                    String userId,
                                                                    @PathVariable("ticketId")
                                                                    @NotBlank
                                                                    @Pattern(regexp = "\\d{6}", message = "Ticket ID must be 6 digit number")
                                                                    String ticketId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", userService.createUserAndLottery(userId, ticketId)));
    }

    @GetMapping("/{userId}/lotteries")
    public LotteryDetailDto getUserBuyLotteryDetail(@PathVariable("userId")
                                                                @NotBlank(message = "User ID value must not be blank")
                                                                @Pattern(regexp = "\\d{10}", message = "User ID must be 10 digit number")
                                                                String userId) {
        return userService.getUserDetail(userId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> sellLottery(@PathVariable("userId")
                                                           @NotBlank(message = "User ID value must not be blank")
                                                           @Pattern(regexp = "\\d{10}", message = "User ID must be 10 digit number")
                                                           String userId,
                                                           @PathVariable("ticketId")
                                                           @NotBlank
                                                           @Pattern(regexp = "\\d{6}", message = "Ticket ID must be 6 digit number")
                                                           String ticketId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("ticket", userService.sellLotteryByUserIdAndTicketId(userId, ticketId)));
    }
}
