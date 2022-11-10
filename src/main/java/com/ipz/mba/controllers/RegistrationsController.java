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

import java.util.Map;

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
    public Map<String, String> register(@RequestBody ClientDataRegistration clientData) {
        String jwtToken;
        try {
            log.info("LOG: " + clientData);
            registrationService.saveData(clientData);
            jwtToken = jwtUtil.generateToken(clientData.getIpn());
        } catch (Exception ex) {
            return Map.of("error", ex.getMessage());
        }
        return Map.of("jwt", jwtToken);
    }
}