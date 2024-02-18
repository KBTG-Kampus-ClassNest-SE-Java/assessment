package com.kbtg.bootcamp.posttest.user.repository;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

  List<UserTicket> findAllByUser(User user);

  @Modifying
  @Transactional
  void deleteByUserAndLottery(User user, Lottery lottery);
}
