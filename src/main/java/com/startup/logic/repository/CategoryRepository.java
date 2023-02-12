package com.startup.logic.repository;
import com.startup.logic.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findAll();
    Long getParentIdByCategory(Category category);

    @Query(nativeQuery = true,
           value = "SELECT * FROM category WHERE parent_id IS NULL")
    List<Category> findAllWhereParentIdIsNull();
}