package com.kbtg.bootcamp.posttest.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // enables Spring security feature in application
public class SecurityConfiguration  {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests((requests) -> requests
            .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
            .requestMatchers("/test/**").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .build();
    }

}
