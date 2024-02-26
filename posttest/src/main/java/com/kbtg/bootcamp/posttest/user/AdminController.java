package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.response.CreateLotteryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/lotteries")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateLotteryResponse> createLottery(@Valid @RequestBody LotteryRequestDto requestDto) {

        this.adminService.createLottery(requestDto);

        CreateLotteryResponse createLotteryResponse = new CreateLotteryResponse();

        createLotteryResponse.setTicket(requestDto.getTicket());

        return new ResponseEntity<>(createLotteryResponse, HttpStatus.OK);
    }
}