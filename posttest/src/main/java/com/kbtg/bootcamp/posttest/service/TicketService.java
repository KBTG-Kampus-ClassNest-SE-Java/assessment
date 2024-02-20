package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpTicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements ImpTicketService {

    private final UserTicketRepository userTicketRepository;

    public TicketService(UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;
    }

    @Override
    public UserTicketEntity buyLottery(UserTicketEntity userTicketEntity) {
        return userTicketRepository.save(userTicketEntity);

    }

//    @Override
//    public String  refundLottery(String ticket,String UserId) {
//        return userTicketRepository.deleteById(ticket);
//    }
}
