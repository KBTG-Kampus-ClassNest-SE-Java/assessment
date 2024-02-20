package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.features.user.lottery.model.buy_lottery.BuyLotteryResDto;
import com.kbtg.bootcamp.posttest.validator.lottery_id.IsLotteryId;
import com.kbtg.bootcamp.posttest.validator.user_id.IsUserId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Validated
public class UserLotteryController {

    private final UserLotteryService userTicketService;

    public UserLotteryController(UserLotteryService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("users/greeting")
    public String greeting() {
        return "Hello lottery";
    }

    @PostMapping("users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResDto> buy(@PathVariable @IsUserId String userId, @PathVariable @IsLotteryId String ticketId) {
        BuyLotteryResDto bodyRes = userTicketService.buy(userId, ticketId);

        return ResponseEntity.ok(bodyRes);
    }
}
