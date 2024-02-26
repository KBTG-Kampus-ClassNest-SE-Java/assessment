package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
