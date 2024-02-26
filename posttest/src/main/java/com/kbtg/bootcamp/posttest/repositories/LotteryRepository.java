package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    @Query("SELECT DISTINCT ticket FROM Lottery WHERE currentAmount > 0 ORDER BY ticket")
    List<String> findDistinctTickets();
}
