package com.kbtg.bootcamp.posttest.repository;


import com.kbtg.bootcamp.posttest.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {

    Lottery findByTicketNumber(String ticketNumber);




}
