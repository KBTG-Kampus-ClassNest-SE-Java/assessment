package com.kbtg.bootcamp.posttest.config;




import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    final String username;
    final String jwtToken;

    if (authHeader == null || !authHeader.startsWith("Bearer")) {
      filterChain.doFilter(request,response);
      return;
    }
    jwtToken = authHeader.substring(7);
    username = jwtService.extractUsername(jwtToken);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      List<GrantedAuthority> authorities = jwtService.getAuthorities(jwtToken);
      if (!jwtService.isTokenExpired(jwtToken)) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request,response);
  }

}