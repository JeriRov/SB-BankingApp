package com.ipz.mba.repositories;

import com.ipz.mba.entities.CardEntity;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.TransactionEntity;
import com.ipz.mba.models.CategorySummary;
import com.ipz.mba.models.RecentSummaryInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllBySenderCardOrReceiverCard(CardEntity senderCard, CardEntity receiverCard);
    @Query(value = "select new com.ipz.mba.models.RecentSummaryInfo(YEAR(t.time), trim(function('to_char', t.time, 'Month')), sum(t.sum), c.currencyName ) from CardEntity c " +
            "join TransactionEntity t on c.cardNumber = t.senderCard.cardNumber  where c.owner = :customer group by YEAR(t.time), function('to_char', t.time, 'Month'), c.currencyName " +
            "order by YEAR(t.time), c.currencyName desc")
    List<RecentSummaryInfo> groupByYearAndMonthAndCurrency(@Param("customer") CustomerEntity customer);

    @Query(value = "select new com.ipz.mba.models.CategorySummary(t.category, sum(t.sum)) from CardEntity c " +
            "join TransactionEntity t on c.cardNumber = t.senderCard.cardNumber " +
            "where c.owner = :customer and c.currencyName=:currency and YEAR(t.time)=:year and trim(function('to_char', t.time, 'Month'))=:month " +
            "group by t.category order by sum(t.sum) desc")
    List<CategorySummary> groupByYearAndMonthAndCurrencyAndCategory(CustomerEntity customer, String currency, int year, String month);
}