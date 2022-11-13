package com.ipz.mba.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_access_secret}")
    private String ACCESS_SECRET;
    @Value("${jwt_refresh_secret}")
    private String REFRESH_SECRET;
    @Value("${jwt_subject}")
    private String SUBJECT;
    @Value("${jwt_issuer}")
    private String ISSUER;
    private final int ACCESS_EXP_MINUTES = 60;
    private final int REFRESH_EXP_DAYS = 30;

    public String generateAccessToken(String phoneOrIpn) {
        Date creationDate = Date.from(ZonedDateTime.now().toInstant());
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(ACCESS_EXP_MINUTES).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("phoneOrIpn", phoneOrIpn)
                .withIssuedAt(creationDate)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(ACCESS_SECRET));
    }

    public String validateAccessToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(ACCESS_SECRET))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        System.out.println(decodedJWT);
        // return phoneOrIpn
        return decodedJWT.getClaim("phoneOrIpn").asString();
    }

    public String generateRefreshToken(String phoneNumber, String ipn) {
        Date creationDate = Date.from(ZonedDateTime.now().toInstant());
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(REFRESH_EXP_DAYS).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("phoneNumber", phoneNumber)
                .withClaim("ipn", ipn)
                .withIssuedAt(creationDate)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(REFRESH_SECRET));
    }

    public String validateRefreshToken(String refreshToken) {
        JWTVerifier verifier = getJWTVerifier(REFRESH_SECRET);
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        System.out.println(decodedJWT);
        // return phoneNumber
        return decodedJWT.getClaim("phoneNumber").asString();
    }

    private JWTVerifier getJWTVerifier(String secretKey) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();
    }
}
