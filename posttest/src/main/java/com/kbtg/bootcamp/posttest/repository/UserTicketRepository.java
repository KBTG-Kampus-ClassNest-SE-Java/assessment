package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserTicketRepository extends JpaRepository<UserTicketEntity, Long> {

    @Query(value = "SELECT * FROM user_ticket WHERE userid = :user_Id", nativeQuery = true)
    List<UserTicketEntity> getAllOwnLotteryFromUser(String user_Id);


//    @Transactional
//    @Modifying
//    @Query(value = "DELETE * FROM user_ticket WHERE userid = :userId", nativeQuery = true)
//    void refundLotteryToStore(String  userId);

//    @Modifying
//    @Query(value = "DELETE * FROM user_ticket WHERE userid = :userId AND ticket = :ticketId")
//    void refundLotteryToStore(String userId, String ticketId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserTicketEntity ut WHERE ut.userid = :userid AND ut.ticket = :ticket")
    void refundLotteryToStore(@Param("userid") String userid, @Param("ticket") String ticket);


//    @Modifying
//    @Query(value = "DELETE FROM UserTicketEntity ut WHERE ut.user_Id = :userId AND ut.ticket = :ticketId")
//    void refundLotteryToStore(@Param("userId") String userId, @Param("ticketId") String ticketId);


//    @Query(value = "DELETE FROM user_ticket WHERE userid = :user_Id", nativeQuery = true)
//    void refundLotteryToStore(@Param("user_Id") String user_Id);
}
