package com.kbtg.bootcamp.posttest.authentication.service.impl;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthenticationResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;
import com.kbtg.bootcamp.posttest.authentication.service.AuthenticationService;
import com.kbtg.bootcamp.posttest.config.JwtService;
import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import java.time.ZonedDateTime;
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
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    } catch (Exception e) {
      throw new BadRequestException("Invalid username or password");
    }
    User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
        () -> new NotFoundException("User not found")
    );
    return new AuthenticationResponse(jwtService.generateToken(user),ZonedDateTime.now());
  }


  @Override
  public AuthenticationResponse register(RegisterRequest request) {
    userRepository.findDistinctByUsername(request.getUsername()).ifPresent(
        user -> {
          throw new BadRequestException("User already exists");
        }
    );

    User user = userRepository.save(User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build());
    return new AuthenticationResponse(jwtService.generateToken(user), ZonedDateTime.now());
  }
}
