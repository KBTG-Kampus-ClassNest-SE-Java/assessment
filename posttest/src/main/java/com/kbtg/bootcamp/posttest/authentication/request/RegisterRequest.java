package com.kbtg.bootcamp.posttest.authentication.request;


import com.kbtg.bootcamp.posttest.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String username;
  private String password;
  private Role role;
}