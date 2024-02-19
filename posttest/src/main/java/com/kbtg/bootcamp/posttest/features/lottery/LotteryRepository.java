package com.kbtg.bootcamp.posttest.features.lottery;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    Optional<Lottery> findByTicketIdAndPrice(String ticketId, BigDecimal price);

    @Query("SELECT l.ticketId FROM Lottery l WHERE l.amount > 0 GROUP BY l.ticketId")
    List<String> findAllAvailableLottery();
}
