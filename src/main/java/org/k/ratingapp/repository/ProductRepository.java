package org.k.ratingapp.repository;

import org.k.ratingapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository
    extends MongoRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
  List<Product> findByTitleStartingWith(String regexp);
}
