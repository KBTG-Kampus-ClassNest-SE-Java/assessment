package com.kbtg.bootcamp.posttest.user.repository;

import com.kbtg.bootcamp.posttest.entity.UserTicket;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {

  boolean existsByUserIdAndLotteryId(int userId, int ticketId);

  Collection<UserTicket> findByUserId(int userId);

  Optional<UserTicket> findByUserIdAndLotteryId(int userId, int ticketId);
}
