package com.ipz.mba.controllers;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.models.NewCardData;
import com.ipz.mba.services.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class NewCardController {

    CardService cardService;

    @Autowired
    public NewCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(path = "/user/newcard")
    public Map<String, CardEntity> login(@RequestBody NewCardData ncd) {
        if(ncd.getPin() == null || ncd.getOwnerId() == -1 || ncd.getTypeId() == -1)
            return Map.of("error", new CardEntity());
        return Map.of("new card", cardService.createCard(
                ncd.isVisa(), ncd.getOwnerId(), ncd.getPin(), ncd.getTypeId(), ncd.getCurrency()));
    }

}
