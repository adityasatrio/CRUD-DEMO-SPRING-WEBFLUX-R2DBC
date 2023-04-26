package com.crud.example.democrud.configs.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtValidationUtil {

    private final JwtKeyUtil jwtKeyUtil;

    public JwtValidationUtil(JwtKeyUtil jwtKeyUtil1) {
        this.jwtKeyUtil = jwtKeyUtil1;
    }

    @Value("${application.security.jwt.secretkey}")
    private String secretKey;

    public Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKeyUtil.getPrivateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getPhone(String token) {
        return getClaimsToken(token).getSubject();
    }
    public Date getExpiryTime(String token) {
        return getClaimsToken(token).getExpiration();
    }
    private Boolean isExpired(String token) {
        return getExpiryTime(token).before(new Date());
    }
    public boolean isTokenValid(String token, String requestUsername) {
        final String username = getPhone(token);
        return (username.equals(requestUsername)) && !isExpired(token);
    }
}
