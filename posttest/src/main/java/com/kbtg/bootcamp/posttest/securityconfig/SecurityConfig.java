package com.kbtg.bootcamp.posttest.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .requestMatchers("/lotteries","/users/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new ApiKeyAuthFilter(), BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();

    }
}
