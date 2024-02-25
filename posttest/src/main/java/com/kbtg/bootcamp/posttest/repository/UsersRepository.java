package com.kbtg.bootcamp.posttest.repository;



import com.kbtg.bootcamp.posttest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

   Users findById(Integer id);

}
