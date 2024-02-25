package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.entity.User;
import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLotteryRepository extends JpaRepository<UserLottery, Long> {
    List<UserLottery> findByUser(User user);

}
