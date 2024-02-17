package com.kbtg.bootcamp.posttest.config;

import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public AuthenticationUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("User not found")
    );
  }

}