package com.kbtg.bootcamp.posttest.repositoty;

import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.model.UserTicket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {


    @Query("SELECT DISTINCT ut.lottery FROM UserTicket ut WHERE ut.users.id = :user_id")
     Optional<List<Lottery>> findDistinctLotteriesByUserId(@Param("user_id") Integer user_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserTicket ut WHERE ut.users.id = :user_id AND ut.lottery.id = :ticket_id")
    void deleteUserTicketByUserIdAndTicketId(@Param("user_id") Integer user_id, @Param("ticket_id") Integer ticket_id);



}
