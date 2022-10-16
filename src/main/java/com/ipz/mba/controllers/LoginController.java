package com.ipz.mba.controllers;

import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authManager;
    private final LoginService loginService;
    private final JWTUtil jwtUtil;

    @Autowired
    public LoginController(@Qualifier("authenticationManager") AuthenticationManager authManager, LoginService loginService, JWTUtil jwtUtil) {
        this.authManager = authManager;
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/phone/{phone}/{password}")
    public Map<String, String> loginViaPhone(@PathVariable String phone, @PathVariable String password){
        System.out.println("LOG: Try to log in via phone " + phone);
        var authToken = new UsernamePasswordAuthenticationToken(phone, password);
        try {
            authManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            return Map.of("error", "bad credentials");
        }
        // refreshing jwt by phoneNumber
        String newJwtToken = jwtUtil.generateToken(phone);
        return Map.of("jwt-token", newJwtToken);
    }

    @GetMapping("/ipn/{ipn}/{password}")
    public ResponseEntity<String> loginViaIpn(@PathVariable String ipn, @PathVariable String password){
        System.out.println("LOG: Try to log in via passport " + ipn);
        if (password.equals(loginService.getPasswordByIpn(ipn)))
            return ResponseEntity.ok("successful login");
        return ResponseEntity.badRequest().body("not successful login");
    }
}