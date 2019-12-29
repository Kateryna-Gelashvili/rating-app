package org.k.ratingapp;

import org.apache.commons.lang3.StringUtils;
import org.k.ratingapp.model.Product;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
  private List<Product> products = new ArrayList<>();

  {
    Product coffee = new Product();
    coffee.setId(1L);
    coffee.setTitle("test coffee");
    coffee.setDescription("test coffee description");
    coffee.setCreatedAt(Instant.now());
    coffee.setUpdatedAt(Instant.now());
    products.add(coffee);

    Product tea = new Product();
    tea.setId(2L);
    tea.setTitle("test tea");
    tea.setDescription("test tea description");
    tea.setCreatedAt(Instant.now());
    tea.setUpdatedAt(Instant.now());
    products.add(tea);
  }

  public List<Product> getProducts(String filterValue) {
    if (StringUtils.isEmpty(filterValue)) {
      return products;
    } else {
      return products.stream()
          .filter(p -> StringUtils.startsWith(p.getTitle(), filterValue))
          .collect(toList());
    }
  }

  public void save(Product product) {
    if (product.getId() != null) {
      products.stream()
          .filter(p -> p.getId().equals(product.getId()))
          .findFirst()
          .ifPresent(
              p -> {
                p.setTitle(product.getTitle());
                p.setDescription(product.getDescription());
                p.setUpdatedAt(Instant.now());
              });
    } else {
      Product newProduct = new Product();
      newProduct.setId(3L);
      newProduct.setTitle(product.getTitle());
      newProduct.setDescription(product.getDescription());
      newProduct.setCreatedAt(Instant.now());
      newProduct.setUpdatedAt(Instant.now());
      products.add(newProduct);
    }
  }

  public void delete(Long id) {
    products.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .ifPresent(
            p -> {
              products.remove(p);
            });
  }
}
