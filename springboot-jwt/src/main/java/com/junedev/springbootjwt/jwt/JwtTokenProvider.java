package com.junedev.springbootjwt.jwt;

import com.junedev.springbootjwt.dto.auth.CustomClaimDto;
import com.junedev.springbootjwt.dto.auth.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenProvider {private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private final int expiredTime;
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String s, @Value("${jwt.expire}") String e) {
        this.expiredTime = Integer.parseInt(e) * 24;
        var keyBytes = Decoders.BASE64.decode(s);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto genToken(CustomClaimDto customClaimDto, Authentication authentication) {
        var auth = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        var exIn = new Date(new Date().getTime() + expiredTime);

        var at = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(exIn)
                .claim(AUTHORITIES_KEY, auth)
                .claim("title", customClaimDto.getTitle())
                .claim("firstName", customClaimDto.getFirstName())
                .claim("lastName", customClaimDto.getLastName())
                .claim("email", customClaimDto.getEmail())
                .claim("phone", customClaimDto.getPhone())
                .claim("gender", customClaimDto.getGender())
                .claim("lastVisited", customClaimDto.getLastVisited())
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(at)
                .tokenExpiresIn(exIn.getTime())
                .build();
    }

    public Authentication getAuth(String a) {
        var claims = parseClaims(a);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("NOT AUTHORISED TOKEN");
        }

        var auth = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var p = new User(claims.getSubject(), "", auth);
        return new UsernamePasswordAuthenticationToken(p, "", auth);
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new RuntimeException("Incorrect," + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired, " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported, " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("InCorrect, " + e.getMessage());
        }
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
