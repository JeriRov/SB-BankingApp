package com.ipz.mba.controllers;

import com.ipz.mba.models.ClientDataRegistration;
import com.ipz.mba.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationsController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody ClientDataRegistration clientData) {
        try {
            System.out.println("LOG: " + clientData);
            registrationService.saveData(clientData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Client was successfully registered.");
    }
}
