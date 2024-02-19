package com.kbtg.bootcamp.posttest.service.impl;

import com.kbtg.bootcamp.posttest.dto.request.CreateTicketRequestDTO;
import com.kbtg.bootcamp.posttest.dto.response.CreateTicketResponseDTO;
import com.kbtg.bootcamp.posttest.entity.Ticket;
import com.kbtg.bootcamp.posttest.exception.IllegalOperationException;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO createTicketRequestDTO) {

        if(ticketRepository.findById(createTicketRequestDTO.getTicket()).isPresent()) {
            throw new IllegalOperationException("Ticket is already exists");
        }

        Ticket ticket = new Ticket();

        ticket.setTicket_no(createTicketRequestDTO.getTicket());
        ticket.setPrice(createTicketRequestDTO.getPrice());
        ticket.setAmount(createTicketRequestDTO.getAmount());

        Ticket createdTicket = ticketRepository.save(ticket);

        return new CreateTicketResponseDTO(createdTicket.getTicket_no());

    }
}
