package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import com.kbtg.bootcamp.posttest.service.impl.ImpUserTicketService;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserLotteryController {

    private final ImpLotteryService impLotteryService;
    private final ImpUserTicketService impUserTicketService;


    public UserLotteryController(ImpLotteryService impLotteryService, ImpUserTicketService impUserTicketService) {
        this.impLotteryService = impLotteryService;
        this.impUserTicketService = impUserTicketService;
    }

    @Description("USE BY USER FOR GET ALL LOTTERY THAT STILL REMAIN IN STORE")
    @GetMapping("/users/lotteries")
    public List<LotteryEntity> getRemainLotteryFromStore() {

        try {
            List<LotteryEntity> lotteryRemain = impLotteryService.getRemainLotteryFromStore();
            if (lotteryRemain == null) {

                return Collections.emptyList();
            } else {
                return lotteryRemain;
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

    @Description("USE BY USER FOR GET ALL LOTTERY THAT ALREADY BOUGHT ")
    @GetMapping("/users/lotteries/{id}")
    public List<UserTicketEntity> getAllOwnLotteryFromUser(@PathVariable("id") String user_Id) {

        List<UserTicketEntity> ownLottery = impUserTicketService.getAllOwnLotteryFromUser(user_Id);
        if (ownLottery == null) {

            return Collections.emptyList();
        } else {
            return ownLottery;
        }
    }

    @Description("USE BY USER FOR BUY LOTTERY FROM STORE")
    @PostMapping("/users/lotteries")
    public ResponseEntity<UserTicketEntity> buyLotteryFromStore(@RequestBody UserTicketEntity userTicketEntity) {

        boolean status = true;
        impLotteryService.updateStatusLottery(userTicketEntity.getTicket(), status);

        UserTicketEntity savedUserTicket = impUserTicketService.buyLotteryFromStore(userTicketEntity);
        return ResponseEntity.ok(savedUserTicket);

    }

    @Description("USE BY USER FOR REFUND LOTTERY TO STORE")
    @DeleteMapping("/users/{userid}/lotteries/{ticket}")
    public void refundLotteryToStore(@PathVariable String userid, @PathVariable String ticket) {

        // Remove record from user_ticket table
        impUserTicketService.refundLotteryToStore(userid, ticket);

        // Update status to false in lottery table
        boolean status = false;
        impLotteryService.updateStatusLottery(ticket, status);
    }


}
