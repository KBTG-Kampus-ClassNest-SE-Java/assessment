package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery,String> {
    @Query(
            value = "SELECT * FROM lottery l WHERE l.amount > 0",
            nativeQuery = true)
    List<Lottery> findAllAvailableLotteries();
}
