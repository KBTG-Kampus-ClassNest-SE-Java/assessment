package com.kbtg.bootcamp.posttest.user.repository;

import com.kbtg.bootcamp.posttest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
