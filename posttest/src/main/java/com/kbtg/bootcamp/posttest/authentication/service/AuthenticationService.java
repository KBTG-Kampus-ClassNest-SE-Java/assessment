package com.kbtg.bootcamp.posttest.authentication.service;

import com.kbtg.bootcamp.posttest.authentication.dto.AuthenticationResponse;
import com.kbtg.bootcamp.posttest.authentication.request.AuthenticationRequest;
import com.kbtg.bootcamp.posttest.authentication.request.RegisterRequest;

public interface AuthenticationService {

  AuthenticationResponse login(AuthenticationRequest request);

  AuthenticationResponse register(RegisterRequest request);
}
