package org.k.ratingapp.repository;

import org.k.ratingapp.model.Product;
import org.k.ratingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<Product> {
  User findByName(String name);
}
