package com.kbtg.bootcamp.posttest.user_ticket;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface User_ticketRepository extends JpaRepository<User_ticket, Integer> {
    List<User_ticket> findByUserid(String userId);

    @Query("SELECT ut FROM User_ticket ut INNER JOIN ut.lottery l WHERE ut.userid = :userid AND l.ticket = :ticket")
    @Transactional
    Optional<User_ticket> findByUserIdAndTicket(@Param("userid") String userId, @Param("ticket") String ticketId);

//    @Modifying
//    @Query("UPDATE User_ticket ut SET ut.lottery = null WHERE ut.userId = :userId AND ut.lottery.ticket = :ticketId")
//    void deleteByUserIdAndTicket(@Param("userId") String userId, @Param("ticketId") String ticketId);
}
