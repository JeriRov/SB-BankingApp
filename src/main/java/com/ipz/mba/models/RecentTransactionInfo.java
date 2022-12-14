package com.ipz.mba.models;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.TransactionEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Getter
public class RecentTransactionInfo implements Comparable<RecentTransactionInfo> {
    private final long id;
    private final String provider;
    private final String currency;
    private final ZonedDateTime time;
    private final BigDecimal sum;
    private final String purpose;
    private final String category;
    private final boolean profit;

    private RecentTransactionInfo(long id, String provider, String currency, ZonedDateTime time, BigDecimal sum, String purpose, String category, boolean profit) {
        this.id = id;
        this.provider = provider;
        this.currency = currency;
        this.time = time;
        this.sum = sum;
        this.purpose = purpose;
        this.category = category;
        this.profit = profit;
    }

    public static RecentTransactionInfo get(CardEntity card, TransactionEntity transaction) {
        log.info("my card: {} | receiver card: {}", card.getCardNumber(), transaction.getReceiverCardNumber());
        return new RecentTransactionInfo(
                transaction.getId(),
                card.getProviderEntity().getProviderName(),
                card.getCurrencyName(),
                transaction.getTime(),
                transaction.getSenderCardNumber().equals(card.getCardNumber()) ? BigDecimal.valueOf(transaction.getSum()) : transaction.getConvertedSum(),
                transaction.getPurpose(),
                transaction.getCategory().getName(),
                card.getCardNumber().equals(transaction.getReceiverCardNumber())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentTransactionInfo that = (RecentTransactionInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(RecentTransactionInfo o) {
        return this.getTime().compareTo(o.getTime());
    }
}