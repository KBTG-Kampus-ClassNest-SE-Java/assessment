package com.kbtg.bootcamp.posttest.authentication.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  @NotNull(message = "Username cannot be null")
  String username;
  @NotNull(message = "Password cannot be null")
  String password;
}
