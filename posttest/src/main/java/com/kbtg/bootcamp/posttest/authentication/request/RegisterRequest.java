package com.kbtg.bootcamp.posttest.authentication.request;


import com.kbtg.bootcamp.posttest.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotNull(message = "Username cannot be null")
  private String username;
  @NotNull(message = "Password cannot be null")
  private String password;
  private Role role ;
}