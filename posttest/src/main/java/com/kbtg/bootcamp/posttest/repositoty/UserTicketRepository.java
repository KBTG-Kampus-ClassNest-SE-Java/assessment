package com.kbtg.bootcamp.posttest.repositoty;

import com.kbtg.bootcamp.posttest.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {


}
