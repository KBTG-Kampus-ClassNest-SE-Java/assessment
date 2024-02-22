package com.kbtg.bootcamp.posttest.repositoty;

import com.kbtg.bootcamp.posttest.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findById(Integer id);

}
