package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
//@EnableJpaRepositories
public interface LotteryRepository extends JpaRepository<LotteryEntity, Long> {


    @Query(value = "update lottery set status = :active where ticket = :ticket", nativeQuery = true)
    void updateLottery(String ticket, boolean active);


//    void updateLottery(@Param("ticketId") Long id, @Param("status") boolean active);

}
