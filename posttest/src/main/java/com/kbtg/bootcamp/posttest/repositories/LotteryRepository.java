package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {

    @Query("SELECT l FROM Lottery l WHERE l.amount > (SELECT COUNT(u) FROM UserTicket u WHERE u.lottery = l)")
    List<Lottery> findLotteryWithAmountGreaterThanUserTicketsCount();

    Optional<Lottery> findByTicket(String ticket);

}