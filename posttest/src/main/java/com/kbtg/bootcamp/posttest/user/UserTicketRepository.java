package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    List<UserTicket> findByUserId (String userId);
    UserTicket findByUserIdAndTicket(String userId, String ticket);
}
