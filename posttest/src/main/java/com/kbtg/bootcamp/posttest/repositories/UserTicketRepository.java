package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {

    @Query(value = """
               UPDATE user_ticket
               SET amount = amount + :amount
               WHERE user_id = :userId
               AND ticket_id = :ticketId
               RETURNING *
               """, nativeQuery = true)
    UserTicket updateTicketAmountOfUser(
            @Param("userId") String userId,
            @Param("ticketId") String ticketId,
            @Param("amount") int amount
    );

    @Query(value = """
            SELECT COUNT(u) > 0
            FROM UserTicket u
            WHERE u.userId = :userId
            AND u.lottery.ticket = :ticketId
            """)
    boolean existsByUserIdAndTicketId(
            @Param("userId") String userId,
            @Param("ticketId") String ticketId
    );

    List<UserTicket> findByUserId(String userId);

}
