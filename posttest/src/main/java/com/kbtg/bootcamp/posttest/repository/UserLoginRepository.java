package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {

//    @Query("update Customer c set c.name = :name WHERE c.id = :customerId")
//    void setCustomerName(@Param("customerId") Long id, @Param("name") String name);
}
