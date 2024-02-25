package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    @Modifying
    @Query("DELETE FROM UserTicket ut WHERE ut.user_id = :userId and ut.ticket_id = :ticketId")
    void sellLotteries(@Param("userId") String userId, @Param("ticketId") String ticketId);

    @Query("SELECT ut FROM UserTicket ut WHERE ut.user_id = :userId")
    List<UserTicket> findByUserTicketId(@Param("userId") String userId);

}
