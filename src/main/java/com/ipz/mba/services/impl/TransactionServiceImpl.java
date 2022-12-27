package com.ipz.mba.services.impl;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentSummaryInfo;
import com.ipz.mba.models.RecentTransactionInfo;
import com.ipz.mba.repositories.CardRepository;
import com.ipz.mba.repositories.TransactionRepository;
import com.ipz.mba.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public List<RecentTransactionInfo> getAllUserTransactions(CustomerEntity customer) {
        log.info("TransactionService: getAllUserTransactions(customer)");

        List<RecentTransactionInfo> transactions = new LinkedList<>();

        customer.getCards().forEach(card -> transactionRepository.findAllBySenderCardOrReceiverCard(
                card,
                card
        ).forEach(transaction -> transactions.add(new RecentTransactionInfo(card, transaction))));


        // sort by time and profit
        transactions.sort(Collections.reverseOrder(RecentTransactionInfo::compareTo)
                .thenComparing(RecentTransactionInfo::isProfit, Comparator.reverseOrder()));
        return transactions;
    }

    @Override
    public List<RecentSummaryInfo> getUserSummariesTransactions(CustomerEntity customer) {
        log.info("TransactionService: getUserSummariesTransactions(customer)");
        List<RecentSummaryInfo> summaries = transactionRepository.groupByYearAndMonthAndCurrency(customer);
        summaries.forEach(summary -> {
            var categories = transactionRepository.groupByYearAndMonthAndCurrencyAndCategory(customer, summary.getCurrency(), summary.getYear(), summary.getMonth());
            categories.forEach(category -> {
                category.setCurrency(summary.getCurrency());
                category.setTotalSum(summary.getSum());
            });
            summary.setCategories(categories);
        });
        return summaries;
    }

}