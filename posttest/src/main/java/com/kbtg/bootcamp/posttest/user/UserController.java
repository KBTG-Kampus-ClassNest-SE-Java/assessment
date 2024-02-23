package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.user.dto.UserTicketsRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/lotteries")
    public LotteryListResponseDto getLotteryList(){
        return this.userService.getAllLotteries();
    }

//    @PostMapping("/users/{userId}/lotteries/{ticketId}")
//    public ResponseEntity<UserTicketsRequestDto> buyLotteries(
//            @PathVariable("userId")
//            Long userId,
//            @PathVariable("ticketId") @NotBlank @Size(min = 6, max = 6)
//            String ticketId) {
//
//        this.userService.buyLotteryTicket(userId, ticketId);
//        return null;
//    }

}
