package com.kbtg.bootcamp.posttest.Lottery;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    Optional<Lottery> findByTicket(String ticketId);

    @Query("SELECT u from Lottery u JOIN FETCH u.user_tickets a WHERE a.userid = :userId")
    @Transactional
    List<Lottery> findByUserTicketUserId(String userId);
}
