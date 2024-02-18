package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery,Long> {
}
