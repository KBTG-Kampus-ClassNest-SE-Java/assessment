package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.request.CreateTicketRequestDTO;
import com.kbtg.bootcamp.posttest.dto.response.CreateTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.ListAllTicketsResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.RefundTicketResponseDTO;
import com.kbtg.bootcamp.posttest.entity.Ticket;


public interface TicketService {
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO createTicketRequestDTO);

    public ListAllTicketsResponseDTO listAllTickets();

}
