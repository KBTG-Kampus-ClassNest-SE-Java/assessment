package com.kbtg.bootcamp.posttest.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kbtg.bootcamp.posttest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String email);

  Optional<Object> findDistinctByUsername(String username);
}