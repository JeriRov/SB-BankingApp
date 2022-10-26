package com.ipz.mba.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.exceptions.CardNotActiveException;
import com.ipz.mba.exceptions.CardNotFoundException;
import com.ipz.mba.exceptions.TransactionFailedException;
import com.ipz.mba.models.TransferRequestData;

public interface CardService {
    void performTransaction(CustomerEntity senderEntity, TransferRequestData transferData) throws CardNotFoundException,
            CardNotActiveException, TransactionFailedException;
}
