package com.ipz.mba.repositories;

import com.ipz.mba.entities.CardTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardTypeRepository extends CrudRepository<CardTypeEntity, Long> {
    CardTypeEntity findCardTypeEntitiesById(Long id);
}
