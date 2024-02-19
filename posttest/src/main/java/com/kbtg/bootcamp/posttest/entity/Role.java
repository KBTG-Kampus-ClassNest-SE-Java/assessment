package com.kbtg.bootcamp.posttest.entity;

import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_CREATE;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_DELETE;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_READ;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_UPDATE;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Getter
@RequiredArgsConstructor
public enum Role {
  ADMIN(
      Set.of(
          ADMIN_READ,
          ADMIN_UPDATE,
          ADMIN_DELETE,
          ADMIN_CREATE
      )
  ),

  ;

  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}


