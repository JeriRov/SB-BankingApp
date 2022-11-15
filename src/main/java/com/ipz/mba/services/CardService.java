package com.ipz.mba.services;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.exceptions.CardNotActiveException;
import com.ipz.mba.exceptions.CardNotFoundException;
import com.ipz.mba.exceptions.TransactionFailedException;
import com.ipz.mba.models.TransferRequestData;

import java.util.List;
import java.util.Optional;

public interface CardService {
    void performTransaction(CustomerEntity senderEntity, TransferRequestData transferData) throws CardNotFoundException,
            CardNotActiveException, TransactionFailedException;

    CardEntity createCard(String provider, CustomerEntity customer, String typeName, String currency);

    List<CardEntity> getAllCards(long ownerId);

    Optional<CardEntity> getCard(String number);
}
