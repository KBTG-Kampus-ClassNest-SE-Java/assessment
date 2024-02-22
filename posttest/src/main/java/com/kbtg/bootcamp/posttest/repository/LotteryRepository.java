package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
//@EnableJpaRepositories
public interface LotteryRepository extends JpaRepository<LotteryEntity, Long> {

    //todo USE FOR USER to get remain lottery from store
    @Query(value = "SELECT * FROM lottery where status = false", nativeQuery = true)
    List<LotteryEntity> getRemainLotteryFromStore();


    //todo USE FOR UPDATE STATUS THAT ALREADY BOUGHT OR NOT
    @Query(value = "update lottery set status = true where ticket = '3'", nativeQuery = true)
    void updateStatusLottery(String ticket, boolean status);

}
