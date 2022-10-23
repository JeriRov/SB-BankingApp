package com.ipz.mba.repositories;

import com.ipz.mba.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findUserByIpn(String ipnNumber);
}
