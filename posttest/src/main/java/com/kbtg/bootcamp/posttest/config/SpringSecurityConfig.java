package com.kbtg.bootcamp.posttest.config;

import com.kbtg.bootcamp.posttest.entity.Role;
import com.kbtg.bootcamp.posttest.entity.User;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    http.authorizeHttpRequests(authorize ->
        authorize
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers("/").permitAll()
            .requestMatchers("/users/**").permitAll()
            .requestMatchers("/lotteries/**").permitAll()
            .anyRequest().authenticated()
    );
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User.builder()
        .username("admin")
        .password(bCryptPasswordEncoder().encode("password"))
        .role(Role.ADMIN)
        .build();
    return new InMemoryUserDetailsManager(user);
  }

  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/*/**", configuration);
    source.registerCorsConfiguration("/api/**", configuration);

    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}