package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
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

    //TODO USE BY USER FOR GET ALL LOTTERY THAT ALREADY BOUGHT
    @Query(value = "SELECT * FROM user_ticket WHERE userid = :user_Id", nativeQuery = true)
    List<UserTicketEntity> getAllOwnLotteryFromUser(String user_Id);


    //TODO USE BY USER TO REFUND LOTTERY TO STORE
    @Transactional
    @Modifying
    @Query("DELETE FROM UserTicketEntity ut WHERE ut.userid = :userid AND ut.ticket = :ticket")
    void refundLotteryToStore(@Param("userid") String userid, @Param("ticket") String ticket);



}
