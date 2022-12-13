package com.ipz.mba.repositories;

import com.ipz.mba.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findCategoryEntityByName(String name);
    Optional<CategoryEntity> findCategoryEntityById(long id);
}
