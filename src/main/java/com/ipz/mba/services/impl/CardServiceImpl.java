package com.ipz.mba.services.impl;

import com.ipz.mba.entities.*;
import com.ipz.mba.exceptions.CardNotActiveException;
import com.ipz.mba.exceptions.CardNotFoundException;
import com.ipz.mba.exceptions.CategoryNotFoundException;
import com.ipz.mba.exceptions.TransactionFailedException;
import com.ipz.mba.models.TransferRequestData;
import com.ipz.mba.repositories.*;
import com.ipz.mba.services.CardService;
import com.ipz.mba.utils.CardGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CardServiceImpl implements CardService {
    private final int SCALE = 8;
    private final String UAH_CURRENCY_NAME = "UAH";
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CurrencyRepository currencyRepository;
    private final CardTypeRepository cardTypeRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, TransactionRepository transactionRepository,
                           CategoryRepository categoryRepository, CurrencyRepository currencyRepository,
                           CardTypeRepository cardTypeRepository, ProviderRepository providerRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.currencyRepository = currencyRepository;
        this.cardTypeRepository = cardTypeRepository;
        this.providerRepository = providerRepository;
    }

    @Transactional
    @Override
    public void performTransaction(CustomerEntity senderEntity, TransferRequestData transferData) throws CardNotFoundException, CardNotActiveException, TransactionFailedException, CategoryNotFoundException {
        log.info("CardService: performTransaction(transferData)");

        // finding cards
        CardEntity senderCardEntity = senderEntity.getCards().stream()
                .filter(card -> card.getCardNumber().equals(transferData.getSenderCardNumber()))
                .findAny()
                .orElseThrow(() -> new CardNotFoundException("Sender do not have that card"));

        CardEntity receiverCardEntity = cardRepository.findByCardNumber(transferData.getReceiverCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Receiver card was not found"));

        // finding categories
        if(transferData.getCategory() == null){
            transferData.setCategory(categoryRepository.findCategoryEntityById(0)
                    .orElseThrow(() -> new CategoryNotFoundException("Category was not found")).getName());
        }
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByName(transferData.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category was not found"));

        // validate sum and cards
        Long initialSum = transferData.getSum();
        validateAll(senderCardEntity, receiverCardEntity, initialSum);

        // defining card currencies
        CurrencyEntity senderCardCurrency = currencyRepository.findByCurrencyName(senderCardEntity.getCurrencyName()).orElseThrow();
        CurrencyEntity receiverCardCurrency = currencyRepository.findByCurrencyName(receiverCardEntity.getCurrencyName()).orElseThrow();

        // converting sender`s transactionSum to receiver`s currency
        BigDecimal convertedSum = convertTransactionalSum(senderCardCurrency, receiverCardCurrency, initialSum);

        // do money transfer between cards
        senderCardEntity.setSum(senderCardEntity.getSum().subtract(BigDecimal.valueOf(initialSum)));
        receiverCardEntity.setSum(receiverCardEntity.getSum().add(convertedSum));

        // set transaction category


        log.info("Category: {}", categoryEntity);

        // creating transaction
        TransactionEntity transaction = new TransactionEntity(
                senderCardEntity.getCardNumber(),
                receiverCardEntity.getCardNumber(),
                transferData.getPurpose(),
                ZonedDateTime.now(),
                transferData.getSum(),
                convertedSum,
                categoryEntity
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
            } else if (receiverCardCurrency.getCurrencyName().equals(UAH_CURRENCY_NAME)) {
                transactionalSum = transactionalSum.multiply(senderCardCurrency.getBuyingExchangeRate());
            } else {
                // convert transactionalSum to uah at first
                transactionalSum = transactionalSum.multiply(senderCardCurrency.getBuyingExchangeRate());

                // convert transactionalSum to receiver`s card currency
                transactionalSum = transactionalSum.divide(receiverCardCurrency.getSalesExchangeRate(), SCALE, RoundingMode.HALF_UP);
            }
        }
        return transactionalSum;
    }

    public CardEntity createCard(String providerName, CustomerEntity customer, String typeName, String currency) {
        CardGenerator cardGenerator = new CardGenerator();
        CardTypeEntity type = cardTypeRepository.findCardTypeEntitiesByName(typeName);
        long count = cardRepository.count();
        ProviderEntity provider = providerRepository.findByProviderName(providerName);
        CardEntity card = cardGenerator.createCard(provider, customer, type, currency, count);
        cardRepository.save(card);
        return card;
    }

    public List<CardEntity> getAllCards(long ownerId) {
        return cardRepository.findCardEntitiesByOwnerId(ownerId);
    }

    public Optional<CardEntity> getCard(String number) {
        return cardRepository.findByCardNumber(number);
    }

    public Map<String, String> changePin(String number, long ownerId) {
        Optional<CardEntity> card = getCard(number);
        if (card.isEmpty())
            return Map.of("error", "Card not found");
        if (ownerId != card.get().getOwner().getId())
            return Map.of("error", "Wrong owner");
        CardGenerator cardGenerator = new CardGenerator();
        String pin = cardGenerator.generatePin();
        card.get().setPinCode(pin);
        cardRepository.save(card.get());
        return Map.of("ok", pin);
    }

    private void validateAll(CardEntity senderCardEntity, CardEntity receiverCardEntity, Long sum) throws CardNotActiveException, TransactionFailedException {
        CardEntity.validate(senderCardEntity);
        CardEntity.validate(receiverCardEntity);

        if (senderCardEntity.getSumLimit() < sum) {
            throw new TransactionFailedException("Limit is lower than specified sum");
        } else if (senderCardEntity.getSum().compareTo(BigDecimal.valueOf(sum)) < 0) {
            throw new TransactionFailedException("Not enough money on the card");
        }
    }

}
