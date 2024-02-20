package com.kbtg.bootcamp.lottery.repository;

import com.kbtg.bootcamp.lottery.entity.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery, String> {

}