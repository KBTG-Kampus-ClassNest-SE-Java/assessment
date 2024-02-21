package com.kbtg.bootcamp.posttest.repositoty;

import com.kbtg.bootcamp.posttest.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {

}
