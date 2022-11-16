package com.ipz.mba.controllers;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentTransactionInfo;
import com.ipz.mba.models.TransferRequestData;
import com.ipz.mba.security.models.CustomerDetails;
import com.ipz.mba.services.CardService;
import com.ipz.mba.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/transactions")
public class TransactionsController {

    private final CardService cardService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(CardService cardService, TransactionService transactionService) {
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> performTransaction(@RequestBody TransferRequestData data) {
        log.info("TransactionsController: performTransaction(data)");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();

        try {
            log.info(data.toString());
            if (!TransferRequestData.isValid(data)) {
                throw new Exception("Transactions data is not valid");
            }
            cardService.performTransaction(customer, data);

            return ResponseEntity.ok(Map.of("message", "Success"));
        } catch (Exception ex) {
            log.error("performTransaction(data): {}", ex.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/all")
    public List<RecentTransactionInfo> getAllUserTransactions() {
        log.info("TransactionsController: getAllUserTransactions()");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();

        return transactionService.getAllUserTransactions(customer);
    }

}
