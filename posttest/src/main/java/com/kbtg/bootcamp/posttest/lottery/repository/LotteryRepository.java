package com.kbtg.bootcamp.posttest.lottery.repository;


import com.kbtg.bootcamp.posttest.entity.Lottery;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {


  Optional<Object> findDistinctByTicket(String ticket);


  Optional<Lottery> findByTicket(String ticket);
}
