package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotteryRepository extends JpaRepository<Lottery,Long> {


    public Optional<Lottery> findTopByTicketNumberAndAmountGreaterThan (String ticketNumber,int Amount );

}
