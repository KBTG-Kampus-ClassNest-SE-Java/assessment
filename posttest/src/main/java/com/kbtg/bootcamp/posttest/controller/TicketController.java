package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.response.ListAllTicketsResponseDTO;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<?> getAllLotteries() {
        ListAllTicketsResponseDTO listAllTicketsResponseDTO = ticketService.listAllTickets();
        return new ResponseEntity<>(listAllTicketsResponseDTO, HttpStatus.OK);
    }
}
