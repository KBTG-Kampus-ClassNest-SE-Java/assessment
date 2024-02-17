package com.kbtg.bootcamp.posttest.authentication.service.impl;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;
import com.kbtg.bootcamp.posttest.authentication.service.AuthenticationService;
import com.kbtg.bootcamp.posttest.config.JwtService;
import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }


  @Override
  public String login(AuthenticationRequest authenticationRequest) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));
    User user = userRepository.findByUsername(authenticationRequest.username()).orElseThrow(
        () -> new RuntimeException("User not found")
    );
    return jwtService.generateToken(user);
  }

  @Override
  public AuthResponse register(RegisterRequest request) {
    userRepository.findDistinctByUsername(request.getUsername()).ifPresent(
        user -> {
          throw new RuntimeException("User already exists");
        }
    );
    User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .userType(request.getRole())
        .build();
    userRepository.save(user);
    return new AuthResponse(jwtService.generateToken(user));
  }
}
