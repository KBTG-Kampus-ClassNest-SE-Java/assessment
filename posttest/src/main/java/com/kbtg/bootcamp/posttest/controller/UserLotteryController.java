package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import com.kbtg.bootcamp.posttest.service.impl.ImpTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/lotteries")
public class UserLotteryController {

    private final ImpLotteryService impLotteryService;
    private final ImpTicketService impTicketService;

    public UserLotteryController(ImpLotteryService ImpLotteryService, ImpTicketService impTicketService) {
        this.impLotteryService = ImpLotteryService;
        this.impTicketService = impTicketService;
    }

    @GetMapping("")
    public List<LotteryEntity> findAllLottery() {
        return impLotteryService.findAllLottery();
    }

    @GetMapping("/{id}")
    public Optional<LotteryEntity> findLotteryById(@PathVariable("id") Long id) {
        return impLotteryService.findLotteryById(id);
    }

    @PostMapping("")
    public void buyLottery(@RequestBody UserTicketEntity userTicketEntity) {
        // update true/false
//        impLotteryService.updateLottery(userTicketEntity.getTicket(), true);

        // insert ticket to table user ticket
        impTicketService.buyLottery(userTicketEntity);



        return;
    }


//    @PutMapping("")
//    public LotteryEntity updateLottery(@RequestBody LotteryEntity lotteryEntity) {
//        return ILotteryService.updateLottery(lotteryEntity);
//    }

    @DeleteMapping("/{id}")
    public void deleteLotteryById(@PathVariable("id")Long id) {
        impLotteryService.deleteLottery(id);
    }
}
