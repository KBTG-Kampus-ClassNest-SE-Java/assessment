package com.kbtg.bootcamp.lottery.repository;

import com.kbtg.bootcamp.lottery.entity.Lottery;
import com.kbtg.bootcamp.lottery.entity.UserTicket;
import com.kbtg.bootcamp.lottery.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    List<UserTicket> findByUserId(Users userId);
    @Query("SELECT SUM(ut.pricePaid) FROM UserTicket ut WHERE ut.userId = :userId")
    BigDecimal getTotalPricePaidByUserId(@Param("userId") Users userId);

    @Query("SELECT COUNT(ut) FROM UserTicket ut WHERE ut.userId = :userId")
    Long getTicketCountByUserId(@Param("userId") Users userId);

    @Modifying
    @Query("DELETE FROM UserTicket ut WHERE ut.userId = :userId and ut.ticketId = :ticketId")
    void deleteLotteryTicketById(@Param("userId") Users userId, @Param("ticketId") Long ticketId);

    @Query("SELECT ut.lottery FROM UserTicket ut WHERE ut.userId = :userId and ut.ticketId = :ticketId")
    Lottery findByTicketIdAndUserId(@Param("userId") Users userId, @Param("ticketId") Long ticketId);

    @Query("SELECT COUNT(ut) > 0 FROM UserTicket ut WHERE ut.userId = :userId AND ut.ticketId = :ticketId")
    boolean existsByTicketIdAndUserId(@Param("userId") Users userId, @Param("ticketId") String ticketId);

}
