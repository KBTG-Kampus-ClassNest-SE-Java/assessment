package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.features.user.lottery.model.buy_lottery.BuyLotteryResDto;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.get_my_lottery.GetMyLotteryResDto;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.sell_lottery.SellLotteryResDto;
import com.kbtg.bootcamp.posttest.validator.lottery_id.IsLotteryId;
import com.kbtg.bootcamp.posttest.validator.user_id.IsUserId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Validated
public class UserLotteryController {
    private final UserLotteryService userTicketService;

    public UserLotteryController(UserLotteryService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResDto> buy(@PathVariable @IsUserId String userId, @PathVariable @IsLotteryId String ticketId) {
        BuyLotteryResDto bodyRes = userTicketService.buy(userId, ticketId);

        return ResponseEntity.ok(bodyRes);
    }

    @GetMapping("users/{userId}/lotteries")
    public ResponseEntity<GetMyLotteryResDto> getMyLottery(@PathVariable @IsUserId String userId) {
        GetMyLotteryResDto bodyRes = userTicketService.getMyLottery(userId);

        return ResponseEntity.ok(bodyRes);
    }

    @DeleteMapping("users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<SellLotteryResDto> sellLottery(@PathVariable @IsUserId String userId, @PathVariable @IsLotteryId String ticketId) {
        SellLotteryResDto bodyRes = userTicketService.sellLottery(userId, ticketId);

        return ResponseEntity.ok(bodyRes);
    }
}
