package com.ipz.mba.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentSummaryInfo;
import com.ipz.mba.models.RecentTransactionInfo;

import java.util.List;

public interface TransactionService {
    List<RecentTransactionInfo> getAllUserTransactions(CustomerEntity customer);

    List<RecentSummaryInfo> getUserSummariesTransactions(CustomerEntity customer);
}
