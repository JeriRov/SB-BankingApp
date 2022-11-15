package com.ipz.mba.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.RecentTransactionInfo;

import java.util.List;

public interface TransactionService {
    List<RecentTransactionInfo> getAllUserTransactions(CustomerEntity customer);
}
