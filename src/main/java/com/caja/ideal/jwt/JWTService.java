package com.caja.ideal.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTService {
    public static final String SECRET_KEY = "2r5u8xADGKbPeShVmYp3s6v9yBEHMcQfTjWnZr4t7wzCF";
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;
    @Value("${spring.jwt.jwtExpirationTime}")
    private int JWT_EXPIRATION_TIME;


    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignigKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignigKey() {
        byte[] signigKey = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(signigKey);
    }

    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(HashMap<String, Object> extractClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(getSignigKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiredToken(token).before(new Date());
    }

    private Date extractExpiredToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
