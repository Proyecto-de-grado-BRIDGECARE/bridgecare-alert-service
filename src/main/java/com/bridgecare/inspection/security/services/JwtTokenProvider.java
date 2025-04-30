package com.bridgecare.inspection.security.services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenProvider {
    private final String secretKey;

    public JwtTokenProvider() {
        this.secretKey = System.getProperty("JWT_SECRET");
        if (this.secretKey == null || this.secretKey.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET is not set");
        }
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        Claims claims = claimsJws.getPayload();
        String correo = claims.getSubject();

        Object rolesObject = claims.get("roles");
        List<SimpleGrantedAuthority> authorities = rolesObject instanceof List<?>
                ? ((List<?>) rolesObject).stream()
                .filter(role -> role instanceof String) // Ensure each role is a String
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new UsernamePasswordAuthenticationToken(correo, token, authorities);
    }


    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token);
    }

 /* Variente por si acaso
private Jws<Claims> parseToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getKey()) // <-- CORREGIDO
            .build()
            .parseClaimsJws(token);  // <-- CORREGIDO
    }

 */
}
