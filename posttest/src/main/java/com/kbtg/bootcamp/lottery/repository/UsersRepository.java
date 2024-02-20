package com.kbtg.bootcamp.lottery.repository;

import com.kbtg.bootcamp.lottery.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
}
