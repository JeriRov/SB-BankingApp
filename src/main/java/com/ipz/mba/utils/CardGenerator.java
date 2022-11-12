package com.ipz.mba.utils;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CardTypeEntity;
import com.ipz.mba.entities.CustomerEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

public class CardGenerator {

    Random rand;

    public CardGenerator() {
        rand = new Random();
    }

    public CardEntity createCard(boolean visa, CustomerEntity owner, String pin_code, CardTypeEntity type, String currency_name) {
        CardEntity card = new CardEntity();
        card.setCardNumber(generateNumber(visa));
        card.setOwner(owner);
        ZonedDateTime created = ZonedDateTime.now();
        card.setCreationTime(created);
        ZonedDateTime expired = ZonedDateTime.now().plusYears(4);
        card.setExpirationTime(expired);
        card.setCvvCode(generateCVV());
        card.setPinCode(pin_code);
        card.setCardType(type);
        card.setCurrencyName(currency_name);
        card.setSum(new BigDecimal(0));
        card.setSumLimit(0);
        card.setIsBlocked(false);
        return card;
    }

    private String generateNumber(boolean visa) {
        StringBuilder cardNumber;
        //BIN
        if (visa) {
            cardNumber = new StringBuilder("415207");
        } else {
            cardNumber = new StringBuilder("515072");
        }
        for (int i = 0; i < 3; i++) {
            cardNumber.append(Math.abs(rand.nextInt()) % 899 + 100);
        }
        return cardNumber.toString() + luhn(cardNumber.toString());
    }

    public boolean validate(String number) {
        if (number.length() < 16) return false;
        return Integer.parseInt(number.substring(15, 16)) == luhn(number.substring(0, 15));
    }

    private String generateCVV() {
        return String.valueOf(Math.abs(rand.nextInt()) % 899 + 100);
    }

    private int luhn(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            if (i % 2 == 1) {
                sum = sum + Integer.parseInt(number.substring(i, i + 1));
            } else {
                int add = Integer.parseInt(number.substring(i, i + 1)) * 2;
                if (add > 9) add = add % 10 + 1;
                sum += add;
            }
        }
        return (sum * 9) % 10;
    }
}
