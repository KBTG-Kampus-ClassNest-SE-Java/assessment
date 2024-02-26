package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    List<UserTicket> findByUserId(String userId);

    List<UserTicket> findByUserIdAndLotteryTicket(String userId, String ticketId);

    void deleteUserTicketByLotteryIn(List<Lottery> list);

}
