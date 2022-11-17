package com.ipz.mba.utils;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CardTypeEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.ProviderEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

public class CardGenerator {

    private final Random rand;

    public CardGenerator() {
        rand = new Random();
    }

    public CardEntity createCard(ProviderEntity provider, CustomerEntity owner, CardTypeEntity type, String currency, long count) {
        CardEntity card = new CardEntity();
        card.setCardNumber(generateNumber(provider.getId(), count));
        card.setOwner(owner);
        ZonedDateTime created = ZonedDateTime.now();
        card.setCreationTime(created);
        ZonedDateTime expired = ZonedDateTime.now().plusYears(4);
        card.setExpirationTime(expired);
        card.setCvvCode(generateCVV());
        card.setPinCode(generatePin());
        card.setCardType(type);
        card.setProviderEntity(provider);
        card.setCurrencyName(currency);
        card.setSum(new BigDecimal(0));
        card.setSumLimit(10_000);
        card.setIsBlocked(false);
        return card;
    }

    private String generateNumber(long provider, long count) {
        StringBuilder cardNumber;
        //BIN
        if (provider == 2) {
            cardNumber = new StringBuilder("41520713");
        } else {
            cardNumber = new StringBuilder("51507213");
        }
        cardNumber.append(1000000 + count + 1);
        return cardNumber.toString() + luhn(cardNumber.toString());
    }

    private String generateCVV() {
        StringBuilder result = new StringBuilder(String.valueOf(Math.abs(rand.nextInt()) % 1000));
        while (result.length() < 3) result.insert(0, "0");
        return result.toString();
    }

    public String generatePin() {
        StringBuilder result = new StringBuilder(String.valueOf(Math.abs(rand.nextInt()) % 10000));
        while (result.length() < 4) result.insert(0, "0");
        return result.toString();
    }

    public int luhn(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int parseInt = Integer.parseInt(number.substring(i, i + 1));
            if (i % 2 == 1) {
                sum = sum + parseInt;
            } else {
                int add = parseInt * 2;
                if (add > 9) add = add % 10 + 1;
                sum += add;
            }
        }
        return (sum * 9) % 10;
    }
}
