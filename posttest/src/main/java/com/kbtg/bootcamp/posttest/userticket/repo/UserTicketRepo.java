package com.kbtg.bootcamp.posttest.userticket.repo;

import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class UserTicketRepo extends JpaRepository<UserTicket, Integer> {
    List<UserTicket> findByUserIdAndTicketId(String userId, String ticketId);
}
