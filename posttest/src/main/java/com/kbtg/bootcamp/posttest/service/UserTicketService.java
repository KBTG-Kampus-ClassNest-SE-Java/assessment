package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.RefundTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.UserPurchaseHistoryResponseDTO;

public interface UserTicketService {
    public PurchaseTicketResponseDTO purchaseTicket(String userId, String ticketId);

    public UserPurchaseHistoryResponseDTO showHistoryPurchase(String userId);

    public RefundTicketResponseDTO refundTicket(String userId, String ticketId);
}
