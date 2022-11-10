package com.ipz.mba.controllers;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.TransferRequestData;
import com.ipz.mba.security.models.CustomerDetails;
import com.ipz.mba.services.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class CardsController {
    private final CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> performTransaction(@RequestBody TransferRequestData data) {
        log.info("AccountsController: performTransaction(data)");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();

        try {
            log.info(data.toString());
            if (!TransferRequestData.isValid(data)) {
                throw new Exception("transfer data is not valid");
            }
            cardService.performTransaction(customer, data);

            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (Exception ex) {
            log.error("performTransaction(data): {}", ex.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}