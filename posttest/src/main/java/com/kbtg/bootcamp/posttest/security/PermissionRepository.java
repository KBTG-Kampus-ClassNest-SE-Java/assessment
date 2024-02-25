package com.kbtg.bootcamp.posttest.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository <Permission, Long> {
}
