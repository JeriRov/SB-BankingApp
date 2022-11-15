package com.ipz.mba.controllers;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.NewCardData;
import com.ipz.mba.models.TransferRequestData;
import com.ipz.mba.security.models.CustomerDetails;
import com.ipz.mba.services.CardService;
import com.ipz.mba.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user/cards")
public class CardsController {
    private final CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(path = "/new")
    public Map<String, String> newCard(@RequestBody NewCardData ncd) {
        if(!Validation.checkNewCardData(ncd))
            return Map.of("error", "Invalid data");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();
        return Map.of("new card", cardService.createCard(
                ncd.getProvider(), customer, ncd.getType(), ncd.getCurrency()).getCardNumber());
    }

    @GetMapping(path = "/")
    public Map<String, List<CardEntity>> allCards(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();
        List<CardEntity> cards = cardService.getAllCards(customer.getId());
        cards.sort(Comparator.comparing(CardEntity::getCardNumber));
        return Map.of("ok", cards);
    }

    @GetMapping(path = "/{cardNumber}")
    public Map<String, CardEntity> getCard(@PathVariable  String cardNumber) {
        if (!Validation.checkCardByLuhn(cardNumber))
            return Map.of("wrong number", new CardEntity());
        Optional<CardEntity> card = cardService.getCard(cardNumber);
        if (card.isEmpty())
            return Map.of("card not found", new CardEntity());
        return Map.of("ok", card.get());
    }
}