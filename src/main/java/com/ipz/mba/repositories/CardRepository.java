package com.ipz.mba.repositories;

import com.ipz.mba.entities.CardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
}