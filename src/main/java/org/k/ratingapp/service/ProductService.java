package org.k.ratingapp.service;

import org.apache.commons.lang3.StringUtils;
import org.k.ratingapp.model.Product;
import org.k.ratingapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getProducts(String filterValue) {
    if (StringUtils.isEmpty(filterValue)) {
      return productRepository.findAll();
    } else {
      List<Product> products = productRepository.findByTitleStartingWith(filterValue);
      return products;
    }
  }

  public void save(Product product) {
    if (product.getId() != null) {
      productRepository.save(product);
    } else {
      Product newProduct = new Product();
      newProduct.setTitle(product.getTitle());
      newProduct.setDescription(product.getDescription());
      newProduct.setCreatedAt(Instant.now());
      newProduct.setUpdatedAt(Instant.now());
      productRepository.insert(newProduct);
    }
  }

  public void delete(Long id) {
    productRepository.deleteById(id);
  }
}
