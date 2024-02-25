package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.request.LotteryRequest;
import com.kbtg.bootcamp.posttest.response.TicketResponse;
import com.kbtg.bootcamp.posttest.service.LotteryService;


import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;
    AdminController (LotteryService lotteryService){
        this.lotteryService = lotteryService;
    }


    @PostMapping("/lotteries")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse addLottery(@Valid @RequestBody LotteryRequest lotteryRequest, BindingResult bindingResult)throws Exception{

        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(field ->{
                throw new BadRequestException(field.getField()+" : "+field.getDefaultMessage());
            });
            }
        return lotteryService.addTicket(lotteryRequest);
    }




}
