package com.ipz.mba.controllers;

import com.ipz.mba.models.ClientDataRegistration;
import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegistrationsController {
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;

    @Autowired
    public RegistrationsController(RegistrationService registrationService, JWTUtil jwtUtil) {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Map<String, String> register(@RequestBody ClientDataRegistration cdr) {
        var formatter = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        String newAccessToken, newRefreshToken;

        try {
            log.info("LOG: " + cdr);
            newRefreshToken = jwtUtil.generateRefreshToken(cdr.getPhoneNumber(), cdr.getIpn());

            registrationService.saveData(cdr, newRefreshToken);

            newAccessToken = jwtUtil.generateAccessToken(cdr.getIpn());
        } catch (Exception ex) {
            return Map.of("error", ex.getMessage());
        }

        Date refreshExpireDate = jwtUtil.getExpireDate(newRefreshToken, false);
        Date accessExpireDate = jwtUtil.getExpireDate(newAccessToken, true);

        TimeZone tz = TimeZone.getDefault();
        String offsetId = tz.toZoneId().getRules().getStandardOffset(Instant.now()).getId();

        return Map.of(
                "refresh_token", newRefreshToken,
                "refresh_expire_date", formatter.format(refreshExpireDate) + offsetId,
                "access_token", newAccessToken,
                "access_expire_date", formatter.format(accessExpireDate) + offsetId
        );
    }
}