package com.ipz.mba.repositories;

import com.ipz.mba.entities.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllBySenderCardNumberOrReceiverCardNumber(String senderCardNumber, String receiverCardNumber);
}