package com.kbtg.bootcamp.posttest.userticket.repository;

import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    List<UserTicket> findByUser(User user);
}
