package com.vikas.airline.security;

import com.vikas.airline.config.JwtProperties;
import com.vikas.airline.entity.User;
import com.vikas.airline.security.constants.JwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import com.vikas.airline.security.constants.JwtTokenType;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;


    //Returns JWT signing key.
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );

    }

    //Generates Access Token with custom claims.
    public String generateAccessToken(User user) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(JwtClaims.USER_ID, user.getId())
                .claim(JwtClaims.FULL_NAME, user.getFullName())
                .claim(JwtClaims.ROLE, user.getRole().name())
                .claim(JwtClaims.TOKEN_TYPE, JwtTokenType.ACCESS)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();

    }


    //Generates Refresh Token.
    public String generateRefreshToken(User user) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(JwtClaims.TOKEN_TYPE, JwtTokenType.REFRESH)
                .setIssuedAt(now)
                .setExpiration(
                        new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration())
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    //Extract email from token.
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }


    //Validates token.
    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        return extractEmail(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

    //Checks whether token is expired.
    private boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }


    //Parses JWT claims.
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public boolean isValidToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {

        try {

            Claims claims = extractClaims(token);
            return JwtTokenType.REFRESH.equals(
                    claims.get(JwtClaims.TOKEN_TYPE, String.class));

        } catch (Exception ex) {
            return false;
        }
    }
}