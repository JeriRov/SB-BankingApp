package com.ipz.mba.repositories;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    CustomerEntity findByUserEntity(UserEntity user);
}
