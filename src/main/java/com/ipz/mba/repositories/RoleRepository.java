package com.ipz.mba.repositories;

import com.ipz.mba.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
