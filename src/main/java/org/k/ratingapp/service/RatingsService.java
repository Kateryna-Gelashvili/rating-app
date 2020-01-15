package org.k.ratingapp.service;

import org.k.ratingapp.model.Product;
import org.k.ratingapp.model.Rating;
import org.k.ratingapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RatingsService {
  private final ProductRepository productRepository;

  public RatingsService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Rating> getRatings(Long productId) {
    return productRepository
        .findById(productId)
        .map(Product::getRatings)
        .orElse(Collections.emptyList());
  }
}
