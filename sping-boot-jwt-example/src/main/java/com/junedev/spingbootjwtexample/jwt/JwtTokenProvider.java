package com.junedev.spingbootjwtexample.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private String secret;
    private final int expiration;
    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "bearer";

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expire}") String expiration) {
        this.expiration = Integer.parseInt(expiration) * 24;
        var keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            var claims = parseClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new RuntimeException("Incorrect " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired, " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported, " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("InCorrect, " + e.getMessage());
        }
    }

    private boolean isTokenExpired(Claims claims) {
        var expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    public Authentication getAuth(String token) {
        var claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new RuntimeException("Not authorised token");
        }

        var auth = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).toList();
        var principal = new User(claims.getSubject(), "", auth);
        return new UsernamePasswordAuthenticationToken(principal, "", auth);
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
