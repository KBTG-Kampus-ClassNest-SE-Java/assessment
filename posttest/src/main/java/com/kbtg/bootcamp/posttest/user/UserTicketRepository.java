package com.kbtg.bootcamp.posttest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    List<UserTicket> findByUserUserId(@Param("userId") Integer userId);

    UserTicket findFirstByUserUserIdAndLotteryId(@Param("userId") Integer userId, @Param("lotteryId") Integer id);
}
