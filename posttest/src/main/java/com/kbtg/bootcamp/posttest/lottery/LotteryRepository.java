package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {

    Optional<Lottery> findByTicket (String ticket);

    void deleteByTicket(String ticket);

//    void delete(Optional<Lottery> ticket);
}
