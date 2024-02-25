package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTicketRepository extends JpaRepository<UserTicket,Long> {

    List<UserTicket> findByUserID(String userId);

    Optional<UserTicket> findTopByUserIDAndTicketNumber(String userID,String ticketNumber);

}
