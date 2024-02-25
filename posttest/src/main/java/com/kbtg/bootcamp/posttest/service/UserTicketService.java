package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTicketService {

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Transactional
    public void sellLotteries(int id, String ticketId) {
        userTicketRepository.sellLotteries(id,ticketId);
    }

    public int buyLottery(String id, String ticketId) {
        UserTicket userTicket = new UserTicket(id,ticketId);
        userTicketRepository.save(userTicket);
        return userTicket.getId();

    }

    @Transactional
    public List<UserTicket> getLotteriesById(String id) {
        List<UserTicket> userTickets = userTicketRepository.findByUserTicketId(id);
        if(userTickets.isEmpty()){
            throw new NotFoundException("Cannot Find Ticket That Purchased By: " + id);
        }
        return userTickets;
    }
}
