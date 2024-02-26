package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
