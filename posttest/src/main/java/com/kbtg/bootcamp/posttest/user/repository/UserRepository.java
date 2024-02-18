package com.kbtg.bootcamp.posttest.user.repository;

import com.kbtg.bootcamp.posttest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
