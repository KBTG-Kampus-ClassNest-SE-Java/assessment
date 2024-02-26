package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    @Query("SELECT DISTINCT ticket FROM Lottery WHERE amount > 0 ORDER BY ticket")
    List<String> findDistinctTickets();
}
