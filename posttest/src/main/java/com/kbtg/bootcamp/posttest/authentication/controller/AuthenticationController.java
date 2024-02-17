package com.kbtg.bootcamp.posttest.authentication.controller;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;
import com.kbtg.bootcamp.posttest.authentication.service.AuthenticationService;
import com.kbtg.bootcamp.posttest.user.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(
      @RequestBody RegisterRequest registerRequest
  ) {
    return ResponseEntity.ok(authenticationService.register(registerRequest));
  }
  @PostMapping("/login")
  public ResponseEntity<String> authenticate(
      @RequestBody AuthenticationRequest authenticationRequest
  ) {
    return ResponseEntity.ok(authenticationService.login(authenticationRequest));
  }

  @GetMapping
  public AuthenticationResponse test(){
    return AuthenticationResponse.builder().build();
  }



}