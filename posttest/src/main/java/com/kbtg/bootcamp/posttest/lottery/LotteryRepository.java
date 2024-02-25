package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {
    Optional<Lottery> findByTicket(String ticketId);
//    @Query("SELECT ticket FROM Lottery WHERE amount > 0")
//    List<String> getAllTicket();
}
