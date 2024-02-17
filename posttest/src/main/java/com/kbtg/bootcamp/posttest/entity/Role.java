package com.kbtg.bootcamp.posttest.entity;

import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_CREATE;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_DELETE;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_READ;
import static com.kbtg.bootcamp.posttest.entity.Permission.ADMIN_UPDATE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Getter
@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
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


