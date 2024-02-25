package com.kbtg.bootcamp.posttest.securityconfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private String HEADER_NAME  = "X-API-KEY";
    private String API_KEY = "your-api-key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestApiKey = request.getHeader(HEADER_NAME);
        if(StringUtils.hasLength(requestApiKey) && API_KEY.equals(requestApiKey)){
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(requestApiKey,null, Collections.emptyList()));
        }
        filterChain.doFilter(request,response);
    }


}
