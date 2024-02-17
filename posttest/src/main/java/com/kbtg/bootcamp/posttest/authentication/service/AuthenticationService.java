package com.kbtg.bootcamp.posttest.authentication.service;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;

public interface AuthenticationService {

  String login(AuthenticationRequest request);

  AuthResponse register(RegisterRequest request);
}
