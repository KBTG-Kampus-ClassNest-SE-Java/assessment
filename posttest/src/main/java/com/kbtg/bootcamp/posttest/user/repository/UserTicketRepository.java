package com.kbtg.bootcamp.posttest.user.repository;

import com.kbtg.bootcamp.posttest.user.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}