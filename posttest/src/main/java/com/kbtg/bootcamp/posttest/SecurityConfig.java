//package com.kbtg.bootcamp.posttest;
//
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Component
//@EnableWebSecurity
////@Configuration
//public class SecurityConfig {
//
//
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/user/lotteries/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/admin/lotteries").hasAuthority("ADMIN_CREATE")
//                        .requestMatchers(HttpMethod.GET, "/admin/lotteries").hasAuthority("ADMIN_READ")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        UserDetails user = User.withUsername("admin")
//                .password(encoder.encode("password"))
//                .authorities("ADMIN_CREATE", "ADMIN_READ")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//}
