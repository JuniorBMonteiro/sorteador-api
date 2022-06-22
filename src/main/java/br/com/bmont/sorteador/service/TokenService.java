package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${sorteador.jwt.expiration}")
    private String expiration;

    @Value("${sorteador.jwt.secret}")
    private String secret;

    public TokenResponse createToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String type = "Bearer";
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));
        String token = Jwts.builder()
                .setIssuer("sorteador")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenResponse(token, type);
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUsernameByToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
