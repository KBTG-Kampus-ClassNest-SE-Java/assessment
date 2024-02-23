package com.kbtg.bootcamp.posttest.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;



@Component
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((requests) -> {
            requests
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll();
        });
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

}
