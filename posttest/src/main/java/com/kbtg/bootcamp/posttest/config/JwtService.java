package com.kbtg.bootcamp.posttest.config;

import com.kbtg.bootcamp.posttest.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtService {

  @Value("${spring.security.jwt.secret-key}")
  private String SECRET_KEY;
  @Value("${spring.security.jwt.expiration}")
  private long jwtExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public List<GrantedAuthority> getAuthorities(String token) {
    List<String> authorities = extractClaim(token, claims -> claims.get("authorities", List.class));
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  private Claims extractAllClaims(String token) {
    return (Claims) Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parse(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(User userDetails) {
    Map<String, Object> claims = new HashMap<>();
    List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    claims.put("authorities", authorities);

    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(jwtExpiration)))
        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}