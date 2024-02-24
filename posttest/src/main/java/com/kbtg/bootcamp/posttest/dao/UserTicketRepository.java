package com.kbtg.bootcamp.posttest.dao;


import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicketEntity, Long>, JpaSpecificationExecutor<UserTicketEntity> {
    @Query(value = "select * from user_ticket where user_id = :user_id ",nativeQuery = true)
    UserTicketEntity findByUserId(@Param("user_id") Long userId);
}