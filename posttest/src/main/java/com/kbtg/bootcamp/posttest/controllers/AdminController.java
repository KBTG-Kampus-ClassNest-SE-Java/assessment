package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.models.lottery.LotteryRequestDTO;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/lotteries")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TicketIdResponseDTO addLotteries(@Valid @RequestBody LotteryRequestDTO lotteryRequestDTO){
        return adminService.addLotteries(lotteryRequestDTO);
    }
}

