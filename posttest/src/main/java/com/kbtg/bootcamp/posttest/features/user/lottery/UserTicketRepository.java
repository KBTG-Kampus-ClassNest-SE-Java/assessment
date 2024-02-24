package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.entities.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    public List<UserTicket> findByUserIdOrderByTicketIdAsc(String userId);

    public List<UserTicket> findByUserIdAndTicketId(String userId, String ticketId);

    @Modifying
    @Query("DELETE FROM UserTicket ut WHERE ut.userId = :userId AND ut.ticketId = :ticketId")
    public void sellTicket(String userId, String ticketId);
}
