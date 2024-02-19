package com.kbtg.bootcamp.posttest.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.Customizer.withDefaults;

@Component
@EnableWebSecurity // enables Spring security feature in application
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/users/**").permitAll() // if match with path /users/ permit all
                .requestMatchers("/test/**").permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .build();
    }

}
