package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Long> {
    Optional<LotteryTicket> findByTicket(String ticket);
}
