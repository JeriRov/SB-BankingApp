package com.ipz.mba.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ipz.mba.entities.UserEntity;
import com.ipz.mba.models.RefreshToken;
import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/refresh_token")
public class RefreshController {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public RefreshController(JWTUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping
    public Map<String, String> returnTokenPair(@RequestBody RefreshToken body) {
        String phoneNumber;
        try {
            phoneNumber = jwtUtil.validateRefreshToken(body.getRefreshToken());
        } catch (JWTVerificationException ex) {
            return Map.of("error", ex.getMessage());
        }

        if (refreshTokenService.isUserPresentWithRefreshToken(phoneNumber, body.getRefreshToken()) == null) {
            return Map.of("error", "user with such refresh-token was not found");
        }

        String newRefreshToken = refreshTokenService.switchRefreshToken(phoneNumber);
        String newAccessToken = jwtUtil.generateAccessToken(phoneNumber);

        Date refreshExpireDate = jwtUtil.getExpireDate(newRefreshToken, false);
        Date accessExpireDate = jwtUtil.getExpireDate(newAccessToken, true);

        var formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        return Map.of(
                "refresh_token", newRefreshToken,
                "refresh_expire_date", formatter.format(refreshExpireDate),
                "access_token", newAccessToken,
                "access_expire_date", formatter.format(accessExpireDate)
        );
    }

    @PostMapping("/logout")
    public Map<String, String> logOut(@RequestBody RefreshToken body) {
        String phoneNumber;
        try {
            phoneNumber = jwtUtil.validateRefreshToken(body.getRefreshToken());
        } catch (JWTVerificationException ex) {
            return Map.of("error", ex.getMessage());
        }

        UserEntity user = refreshTokenService.isUserPresentWithRefreshToken(phoneNumber, body.getRefreshToken());
        if (user == null) {
            return Map.of("error", "user with such refresh-token was not found");
        }

        refreshTokenService.updateRefreshToken(user, null);

        return Map.of("success", "logout");
    }

}