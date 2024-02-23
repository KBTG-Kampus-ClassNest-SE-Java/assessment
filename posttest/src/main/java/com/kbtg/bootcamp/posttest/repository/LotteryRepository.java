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
public interface LotteryRepository extends JpaRepository<LotteryEntity, Long> {

    //TODO USE BY USER TO GET REMAIN LOTTERY FROM STORE
    @Query(value = "SELECT * FROM lottery where status = false", nativeQuery = true)
    List<LotteryEntity> getRemainLotteryFromStore();


    //TODO USE FOR UPDATE STATUS THAT ALREADY BOUGHT OR NOT -> CHECK BY TRUE/FALSE
    @Modifying
    @Query(value = "UPDATE lottery SET status = true WHERE ticket = :ticket", nativeQuery = true)
    void updateStatusLottery(@Param("ticket") String ticket);

    @Modifying
    @Query(value = "SELECT * FROM lottery WHERE ticket = :ticket", nativeQuery = true)
    List<LotteryEntity> getTicketEntity(@Param("ticket") String ticket);

}
