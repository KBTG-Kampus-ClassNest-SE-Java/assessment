package com.kbtg.bootcamp.posttest.userTicket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    List<UserTicket> findByUserId(Long userIdLong);

    UserTicket findByUserIdAndLotteryId(Long aLong, Long aLong1);
}
