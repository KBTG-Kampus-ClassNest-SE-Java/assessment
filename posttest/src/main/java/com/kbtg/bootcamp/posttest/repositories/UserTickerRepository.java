package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.models.userTicket.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserTickerRepository extends JpaRepository<UserTicket, Long> {

    @Transactional
    @Modifying
    @Query("delete from UserTicket u where u.userId = ?1 and u.lottery.ticket = ?2")
    Integer deleteByUserIdAndLotteryTicket(String userId, String ticket);

    @Query("select u from UserTicket u where u.userId = ?1")
    List<UserTicket> findByUserId(String userId);

}
