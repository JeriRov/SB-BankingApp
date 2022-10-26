package com.ipz.mba.services.impl;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CurrencyEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.TransactionEntity;
import com.ipz.mba.exceptions.CardNotActiveException;
import com.ipz.mba.exceptions.CardNotFoundException;
import com.ipz.mba.exceptions.TransactionFailedException;
import com.ipz.mba.models.TransferRequestData;
import com.ipz.mba.services.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class CardServiceImpl implements CardService {
    private final int SCALE = 8;
    private final String UAH_CURRENCY_NAME = "UAH";
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           TransactionRepository transactionRepository,
                           CurrencyRepository currencyRepository) {

        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public void performTransaction(CustomerEntity senderEntity, TransferRequestData transferData) throws CardNotFoundException, CardNotActiveException, TransactionFailedException {
        log.info("CardService: performTransaction(transferData)");

        // finding cards
        CardEntity senderCardEntity = senderEntity.getCards().stream()
                .filter(card -> card.getCardNumber().equals(transferData.getSenderCardNumber()))
                .findAny()
                .orElseThrow(() -> new CardNotFoundException("sender do not have that card"));

        CardEntity receiverCardEntity = cardRepository.findByCardNumber(transferData.getReceiverCardNumber())
                .orElseThrow(() -> new CardNotFoundException("receiver card was not found"));


        Long initialSum = transferData.getSum();
        validateAll(senderCardEntity, receiverCardEntity, initialSum);

        // defining card currencies
        CurrencyEntity senderCardCurrency = currencyRepository.findByCurrencyName(senderCardEntity.getCurrencyName()).get();
        CurrencyEntity receiverCardCurrency = currencyRepository.findByCurrencyName(receiverCardEntity.getCurrencyName()).get();

        // converting sender`s transactionSum to receiver`s currency
        BigDecimal convertedSum = convertTransactionalSum(senderCardCurrency, receiverCardCurrency, initialSum);

        // do money transfer between cards
        senderCardEntity.setSum(senderCardEntity.getSum().subtract(BigDecimal.valueOf(initialSum)));
        receiverCardEntity.setSum(receiverCardEntity.getSum().add(convertedSum));

        // creating transaction
        TransactionEntity transaction = new TransactionEntity(
                senderCardEntity.getCardNumber(),
                receiverCardEntity.getCardNumber(),
                transferData.getPurpose(),
                ZonedDateTime.now(),
                transferData.getSum()
        );

        // saving all
        cardRepository.save(senderCardEntity);
        cardRepository.save(receiverCardEntity);
        transactionRepository.save(transaction);

        log.info("CardService: performTransaction(transferData): transaction successfully performed");
    }

    private BigDecimal convertTransactionalSum(CurrencyEntity senderCardCurrency, CurrencyEntity receiverCardCurrency, Long initialSum) {
        BigDecimal transactionalSum = BigDecimal.valueOf(initialSum);

        // if currency don`t match
        if (!senderCardCurrency.equals(receiverCardCurrency)) {
            // if we send money from uah-card
            if (senderCardCurrency.getCurrencyName().equals(UAH_CURRENCY_NAME)) {
                transactionalSum = transactionalSum.divide(receiverCardCurrency.getSalesExchangeRate(), SCALE, RoundingMode.HALF_UP);
            }
            else if (receiverCardCurrency.getCurrencyName().equals(UAH_CURRENCY_NAME)) {
                transactionalSum = transactionalSum.multiply(senderCardCurrency.getBuyingExchangeRate());
            }
            else {
                // convert transactionalSum to uah at first
                transactionalSum = transactionalSum.multiply(senderCardCurrency.getBuyingExchangeRate());

                // convert transactionalSum to receiver`s card currency
                transactionalSum = transactionalSum.divide(receiverCardCurrency.getSalesExchangeRate(), SCALE, RoundingMode.HALF_UP);
            }
        }
        return transactionalSum;
    }

    private void validateAll(CardEntity senderCardEntity, CardEntity receiverCardEntity, Long sum) throws CardNotActiveException, TransactionFailedException {
        CardEntity.validate(senderCardEntity);
        CardEntity.validate(receiverCardEntity);

        if (senderCardEntity.getSumLimit() < sum) {
            throw new TransactionFailedException("limit is lower than specified sum");
        } else if (senderCardEntity.getSum().compareTo(BigDecimal.valueOf(sum)) < 0) {
            throw new TransactionFailedException("not enough money on the card");
        }
    }

}
