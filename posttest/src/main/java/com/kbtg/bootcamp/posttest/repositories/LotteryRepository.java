package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery, String> {
}
