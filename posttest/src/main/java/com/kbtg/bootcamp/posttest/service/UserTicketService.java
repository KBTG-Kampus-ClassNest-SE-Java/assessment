package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;

public interface UserTicketService {
    public PurchaseTicketResponseDTO purchaseTicket(String userId, String ticketId);
}
