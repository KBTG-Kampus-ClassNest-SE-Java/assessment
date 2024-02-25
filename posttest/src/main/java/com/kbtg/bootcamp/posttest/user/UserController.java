package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.payload.LotteryListDetailResponseDto;
import com.kbtg.bootcamp.posttest.payload.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.payload.UserIdResponseDto;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/lotteries/{ticketId}")
    public UserIdResponseDto createUserAndLottery(@PathVariable("userId")
                               @NotBlank(message = "User ID value must not be blank")
                               @Pattern(regexp = "\\d{10}", message = "User ID must be 10 digit number")
                               String userId,
                                        @PathVariable("ticketId")
                               @NotBlank
                               @Pattern(regexp = "\\d{6}", message = "Ticket ID must be 6 digit number")
                               String ticketId) {
        return userService.createUserAndLottery(userId, ticketId);
    }

    @GetMapping("/{userId}/lotteries")
    public LotteryListDetailResponseDto getUserBuyLotteryDetail(@PathVariable("userId")
                                                            @NotBlank
                                                            @Size(min=10, max=10)
                                                            String userId) {
        return userService.getUserDetail(userId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponseDto sellLottery(@PathVariable("userId")
                                                @NotBlank
                                                @Size(min=10, max=10)
                                                String userId,
                                            @PathVariable("ticketId")
                                                @NotBlank
                                                @Size(min=6, max=6)
                                                String ticketId) {
        return userService.sellLotteryByUserIdAndTicketId(userId, ticketId);
    }
}

