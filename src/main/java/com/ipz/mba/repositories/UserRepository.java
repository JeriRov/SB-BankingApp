package com.ipz.mba.repositories;

import com.ipz.mba.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByPhoneNumber(String phoneNumber);
}
