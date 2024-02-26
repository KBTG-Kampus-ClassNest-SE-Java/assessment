package com.kbtg.bootcamp.posttest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    @Query(value = "SELECT u_t.user_ticket_id, u_t.user_id, u_t.ticket_id, u.username, l.ticket, l.price "+
            "FROM user_ticket u_t " +
            "JOIN users u on u_t.user_id = u.user_id " +
            "JOIN lottery l on u_t.ticket_id = l.ticket_id " +
            "WHERE u_t.user_id = :userId",
            nativeQuery = true
    )
    List<Map<String, Object>> findUserTicketDetailByUserId(@Param("userId") String userId);
}
