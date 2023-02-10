package com.startup.logic.repository;
import com.startup.logic.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByName(String name);

    List<Category> findAll();
    List<Category> findAllByName(String name);
}