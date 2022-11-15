package com.ipz.mba.repositories;

import com.ipz.mba.entities.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, Long> {
    Optional<CurrencyEntity> findByCurrencyName(String currencyName);
}