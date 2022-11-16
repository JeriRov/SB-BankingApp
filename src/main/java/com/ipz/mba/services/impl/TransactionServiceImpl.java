package com.ipz.mba.services.impl;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentTransactionInfo;
import com.ipz.mba.repositories.TransactionRepository;
import com.ipz.mba.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        Set<RecentTransactionInfo> transactions = new LinkedHashSet<>();

        customer.getCards().forEach(card -> transactionRepository.findAllBySenderCardNumberOrReceiverCardNumber(
                card.getCardNumber(),
                card.getCardNumber()
        ).forEach(t -> transactions.add(RecentTransactionInfo.get(card, t))));

        // sort by time
        List<RecentTransactionInfo> list = new ArrayList<>(transactions);
        list.sort((t1, t2) -> compareTime(t1.getTime(), t2.getTime()));

        return list;
    }

    private int compareTime(ZonedDateTime first, ZonedDateTime second) {
        return first.isAfter(second) ? -1 : 1;
    }
}