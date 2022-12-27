package com.ipz.mba.models;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CurrencyEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.TransactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecentTransactionInfo implements Comparable<RecentTransactionInfo> {
    protected long id;
    protected String provider;
    protected String currency;
    protected ZonedDateTime time;
    protected BigDecimal sum;
    protected String purpose;
    protected String category;
    protected boolean profit;
    private String senderCardNumber;
    private String receiverCardNumber;
    private String senderName;
    private String receiverName;

    public RecentTransactionInfo(CardEntity card, TransactionEntity transaction) {
        log.info("my card: {} | receiver card: {}", card.getCardNumber(), transaction.getReceiverCard().getCardNumber());
        this.id = transaction.getId();
        this.provider = card.getProviderEntity().getProviderName();
        this.currency = card.getCurrencyName();
        this.time = transaction.getTime();
        this.sum = transaction.getSenderCard().getCardNumber().equals(card.getCardNumber()) ?
                BigDecimal.valueOf(transaction.getSum()) : transaction.getConvertedSum();
        this.purpose = transaction.getPurpose();
        this.category = transaction.getCategory().getName();
        this.profit = card.getCardNumber().equals(transaction.getReceiverCard().getCardNumber());

        this.senderCardNumber = transaction.getSenderCard().getCardNumber();
        this.receiverCardNumber = transaction.getReceiverCard().getCardNumber();

        CustomerEntity sender = transaction.getSenderCard().getOwner();
        CustomerEntity receiver = transaction.getReceiverCard().getOwner();
        this.senderName = sender.getFirstName() + " " + sender.getLastName();
        this.receiverName = receiver.getFirstName() + " " + receiver.getLastName();
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