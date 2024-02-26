package com.kbtg.bootcamp.posttest.authentication;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.security.CustomUserDetail;
import com.kbtg.bootcamp.posttest.user.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Map<String, Object> userDetailInDB = userRepository.findUserRoleAuthorizationByUsername(username);

        if (userDetailInDB.isEmpty()) {
            throw new NotFoundException("User not found.");
        }
        String authUsername = (String) userDetailInDB.get("username");
        String authPassword = (String) userDetailInDB.get("password");
        String authRole = (String) userDetailInDB.get("role");

        CustomUserDetail customUserDetail = new CustomUserDetail(authUsername, authPassword);
        customUserDetail.setRoles(List.of(authRole));
        return customUserDetail;
    }
}
