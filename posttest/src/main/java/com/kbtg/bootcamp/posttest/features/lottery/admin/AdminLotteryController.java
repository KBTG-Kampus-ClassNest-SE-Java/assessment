package com.kbtg.bootcamp.posttest.features.lottery.admin;

import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryReqDto;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryResDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/lotteries")
public class AdminLotteryController {

    private final AdminLotteryService adminLotteryService;

    public AdminLotteryController(AdminLotteryService adminLotteryService) {
        this.adminLotteryService = adminLotteryService;
    }

    @PostMapping("")
    public CreateLotteryResDto createTicket(@RequestBody @Valid CreateLotteryReqDto req) {
        return adminLotteryService.createLottery(req);
    }

}
