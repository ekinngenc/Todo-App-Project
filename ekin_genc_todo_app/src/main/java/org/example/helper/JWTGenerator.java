package org.example.helper;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTGenerator {

    public static final long JWT_EXPIRATION = 3600000;
    public static final String JWT_SECRET = "secret";

    public String checkTokenIsExpiredAndGetUsernameFromToken(String token) {
        if (isTokenExpired(token)){
            throw new ExpiredJwtException(null, null, "JWT Token has expired.");
        }
        String username = getClaimFromToken(token, Claims::getSubject);
        if(Objects.isNull(username)){
            throw new MalformedJwtException("Invalid JWT Token.");
        }
        return username;
    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        String username = authentication.getName();
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentDate.getTime() + JWT_EXPIRATION ))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
