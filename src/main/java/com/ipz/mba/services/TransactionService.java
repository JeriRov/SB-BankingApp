package com.ipz.mba.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.CategorySummary;
import com.ipz.mba.models.RecentSummaryInfo;
import com.ipz.mba.models.RecentTransactionInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<RecentTransactionInfo> getAllUserTransactions(CustomerEntity customer);

    List<RecentSummaryInfo> getUserSummariesTransactions(CustomerEntity customer);
}
