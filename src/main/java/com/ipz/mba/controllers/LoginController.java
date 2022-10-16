package com.ipz.mba.controllers;

import com.ipz.mba.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login/phone/{phone}/{password}")
    public ResponseEntity<Boolean> loginViaPhone(@PathVariable String phone, @PathVariable String password){
        System.out.println("LOG: Try to log in via phone " + phone);
        if(password.equals(loginService.getPasswordByPhone(phone)))
            return ResponseEntity.ok(true);
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/login/passport/{passport}/{password}")
    public ResponseEntity<Boolean> loginViaPassport(@PathVariable String passport, @PathVariable String password){
        System.out.println("LOG: Try to log in via passport " + passport);
        if(password.equals(loginService.getPasswordByIpn(passport)))
            return ResponseEntity.ok(true);
        return ResponseEntity.badRequest().body(false);
    }
}
