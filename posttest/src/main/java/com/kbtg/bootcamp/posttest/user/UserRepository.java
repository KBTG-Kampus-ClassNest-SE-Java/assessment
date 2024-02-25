package com.kbtg.bootcamp.posttest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    @Query(value = "SELECT u.username, u.password, r.role " +
            "FROM user_role u_r " +
            "JOIN users u on u_r.user_id = u.user_id " +
            "JOIN role r on u_r.role_id = r.role_id " +
            "WHERE u.username = :username ",
            nativeQuery = true)
    public Map<String, Object> findUserRoleAuthorizationByUsername(@Param("username") String username);

}
