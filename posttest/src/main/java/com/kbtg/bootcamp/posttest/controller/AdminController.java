package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.request.CreateTicketRequestDTO;
import com.kbtg.bootcamp.posttest.dto.response.CreateTicketResponseDTO;
import com.kbtg.bootcamp.posttest.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/lotteries")
    public ResponseEntity<?> createTicket(@RequestBody @Valid CreateTicketRequestDTO createTicketRequestDTO) {
        CreateTicketResponseDTO createdTicket = ticketService.createTicket(createTicketRequestDTO);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }
}
