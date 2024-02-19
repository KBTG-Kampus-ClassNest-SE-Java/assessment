package com.kbtg.bootcamp.posttest.authentication.controller;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthenticationResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;
import com.kbtg.bootcamp.posttest.authentication.service.AuthenticationService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/authenticate")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @Valid @RequestBody RegisterRequest registerRequest
  ) {
    return ResponseEntity.ok(authenticationService.register(registerRequest));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @Valid @RequestBody AuthenticationRequest authenticationRequest
  ) {
    return ResponseEntity.ok(authenticationService.login(authenticationRequest));
  }


}