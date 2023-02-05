package com.startup.logic.repository;

import com.startup.logic.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByDateBetween(LocalDateTime dateFirst, LocalDateTime dateSecond);
    List<Article> findAll();
    List<Article> findAllByCategoryId(Long categoryId);
}
