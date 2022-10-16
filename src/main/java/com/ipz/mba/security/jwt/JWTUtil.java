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
    @Value("${jwt_secret}")
    private String SECRET;
    @Value("${jwt_subject}")
    private String SUBJECT;
    @Value("${jwt_issuer}")
    private String ISSUER;
    private final int EXP_MINUTES = 60;

    public String generateToken(String phoneOrIpn) {
        Date creationDate = Date.from(ZonedDateTime.now().toInstant());
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(EXP_MINUTES).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("phoneOrIpn", phoneOrIpn)
                .withIssuedAt(creationDate)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        System.out.println(decodedJWT);
        // return phoneOrIpn
        return decodedJWT.getClaim("phoneOrIpn").asString();
    }

}
