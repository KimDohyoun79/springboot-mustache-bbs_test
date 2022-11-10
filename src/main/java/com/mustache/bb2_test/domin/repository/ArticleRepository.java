package com.mustache.bb2_test.domin.repository;

import com.mustache.bb2_test.domin.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
