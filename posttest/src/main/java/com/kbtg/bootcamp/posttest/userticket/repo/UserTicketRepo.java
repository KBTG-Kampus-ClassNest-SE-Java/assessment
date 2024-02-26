package com.kbtg.bootcamp.posttest.userticket.repo;

import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface UserTicketRepo extends JpaRepository<UserTicket, Integer> {
    @Query("SELECT ut FROM UserTicket ut JOIN ut.lottery l WHERE ut.userId = :userId AND l.ticket = :ticketId")
    List<UserTicket> findByUserIdAndTicketId(@Param("userId") String userId, @Param("ticketId") String ticketId);

    List<UserTicket> findByUserId(String userId);
    }
