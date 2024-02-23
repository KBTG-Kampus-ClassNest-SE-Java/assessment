package com.kbtg.bootcamp.posttest.service.impl;


import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;

import java.util.List;

public interface ImpUserTicketService {

    UserTicketEntity buyLotteryFromStore(UserTicketEntity userTicketEntity);

    List<UserTicketEntity> getAllOwnLotteryFromUser(String user_id);

    void refundLotteryToStore(String userid, String ticket);



}
