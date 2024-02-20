package com.kbtg.bootcamp.lottery.repository;

import com.kbtg.bootcamp.lottery.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}
