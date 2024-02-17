package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LotteryController {

    private final LotteryRepository lotteryRepository;

    public LotteryController(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    // Create
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/lotteries")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDto create(@RequestBody Lottery lottery) {
        return new TicketDto(lotteryRepository.save(lottery).getTicket());
    }

    // Read
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/lotteries")
    public TicketsDto read() {
        return new TicketsDto(
                lotteryRepository.findAll()
                        .stream()
                        .map(l -> l.getTicket()).
                        collect(Collectors.toList())
        );
    }
}
