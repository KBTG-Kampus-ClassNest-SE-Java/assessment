package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpUserTicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTicketService implements ImpUserTicketService {

    private final UserTicketRepository userTicketRepository;

    public UserTicketService(UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;

    }

    @Override  //todo USE BY USER for buy lottery
    public UserTicketEntity buyLotteryFromStore(UserTicketEntity userTicketEntity) {
        return userTicketRepository.save(userTicketEntity);
    }

    @Override  //todo USE BY USER to list all lottery that already bought
    public List<UserTicketEntity> getAllOwnLotteryFromUser(String user_id) {
        return userTicketRepository.getAllOwnLotteryFromUser(user_id);
    }

    @Override  //todo USE BY USER refund lottery to store
    public void refundLotteryToStore(String userid, String ticket) {
        userTicketRepository.refundLotteryToStore(userid, ticket);  // รอ database มาใส่
    }




}
