package com.ipz.mba.controllers;

import com.ipz.mba.models.ClientDataLogin;
import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.services.RefreshTokenService;
import com.ipz.mba.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    private final AuthenticationManager authManager;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    @Autowired
    public LoginController(@Qualifier("authenticationManager") AuthenticationManager authManager,
                           RefreshTokenService refreshTokenService, JWTUtil jwtUtil) {
        this.authManager = authManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(@RequestBody ClientDataLogin cdl) {
        String number;
        if (cdl.isPhone()) {
            log.info("LOG: Try to log in via phone {}", cdl.getPhoneNumber());
            number = cdl.getPhoneNumber();
            if (!Validation.checkPhoneNumber(number)) {
                return Map.of("error", "bad phone number");
            }
        } else {
            log.info("LOG: Try to log in via ipn {}", cdl.getIpn());
            number = cdl.getIpn();
            if (!Validation.checkIpn(number)) {
                return Map.of("error", "bad ipn");
            }
        }

        var authToken = new UsernamePasswordAuthenticationToken(number, cdl.getPassword());
        try {
            authManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            return Map.of("error", "bad credentials");
        }

        // generate pair of tokens
        String newRefreshToken = refreshTokenService.switchRefreshToken(number);
        String newAccessToken = jwtUtil.generateAccessToken(number);

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
}