package com.ipz.mba.services.impl;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentTransactionInfo;
import com.ipz.mba.repositories.TransactionRepository;
import com.ipz.mba.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<RecentTransactionInfo> getAllUserTransactions(CustomerEntity customer) {
        log.info("TransactionService: getAllUserTransactions(customer)");
        List<RecentTransactionInfo> transactions = new LinkedList<>();

        customer.getCards().forEach(card -> transactionRepository.findAllBySenderCardNumberOrReceiverCardNumber(
                card.getCardNumber(),
                card.getCardNumber()
        ).forEach(t -> transactions.add(RecentTransactionInfo.get(card, t))));

        // sort by time
        transactions.sort(Collections.reverseOrder(RecentTransactionInfo::compareTo)
                .thenComparing(RecentTransactionInfo::isProfit, Comparator.reverseOrder()));
        return transactions;
    }

}