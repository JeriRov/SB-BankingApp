package com.ipz.mba.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ipz.mba.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

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
        JWTVerifier verifier = getJWTVerifier(ACCESS_SECRET);
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

    public Date getExpireDate(String token, boolean isAccessToken) {
        JWTVerifier verifier = getJWTVerifier(isAccessToken ? ACCESS_SECRET : REFRESH_SECRET);
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getExpiresAt();
    }

    public Map<String, String> getTokensAndExpireDates(String number, RefreshTokenService refreshTokenService) {
        String newRefreshToken = refreshTokenService.switchRefreshToken(number);
        String newAccessToken = generateAccessToken(number);

        Date refreshExpireDate = getExpireDate(newRefreshToken, false);
        Date accessExpireDate = getExpireDate(newAccessToken, true);

        var formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        return Map.of(
                "refresh_token", newRefreshToken,
                "refresh_expire_date", formatter.format(refreshExpireDate),
                "access_token", newAccessToken,
                "access_expire_date", formatter.format(accessExpireDate)
        );
    }
}
