package com.social.network.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.PrivateKey;
import java.security.interfaces.DSAPrivateKey;
import java.util.Base64;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtTokenProvider {

    @Value("${network.app.secret}")
    private String APP_SECRET="mmmmwimdkjcfnskbahjbcajhbcahbcajhcbajsdfbfsbfsbfsbfbsbsbsbsmjhf";

    @Value("${network.expires.in}")
    private long EXPIRES_IN;
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_SECRET);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

//    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(APP_SECRET));
//    Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(APP_SECRET),
//            SignatureAlgorithm.HS512.getJcaName());

    public String generateJwtToken(Authentication authentication) {
        JWTUserDetails userDetails = (JWTUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }

    Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(APP_SECRET).build()
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
