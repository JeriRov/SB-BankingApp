package com.ipz.mba.repositories;

import com.ipz.mba.entities.ProviderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProviderRepository extends CrudRepository<ProviderEntity, Long> {
    ProviderEntity findByProviderName(String name);
}
