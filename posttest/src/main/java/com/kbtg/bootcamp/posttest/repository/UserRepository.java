package com.kbtg.bootcamp.posttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbtg.bootcamp.posttest.model.User;

public interface UserRepository extends JpaRepository<User,String>{


}
