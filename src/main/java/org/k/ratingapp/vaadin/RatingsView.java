package org.k.ratingapp.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import org.k.ratingapp.dto.RatingDto;
import org.k.ratingapp.model.Product;
import org.k.ratingapp.model.Rating;
import org.k.ratingapp.repository.ProductRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Route("ratings")
public class RatingsView extends VerticalLayout implements HasUrlParameter<String> {
  private final ProductRepository productRepository;
  H2 h2 = new H2();
  private Long productId;
  private String productTitle;
  private Grid<RatingDto> grid = new Grid<>(RatingDto.class);

  public RatingsView(ProductRepository productRepository) {
    this.productRepository = productRepository;

    grid.setColumns("userName", "rating", "createdAt", "updatedAt");

    VerticalLayout mainContent = new VerticalLayout(h2, grid);
    mainContent.setSizeFull();

    add(mainContent);
    setSizeFull();
  }

  public void updateList() {
    if (productId != null) {
      Optional<Product> product = productRepository.findById(productId);
      product.ifPresent(
          p -> {
            List<Rating> ratings = p.getRatings();
            if (ratings != null) {
              List<RatingDto> ratingDtos =
                  ratings.stream().map(this::getRatingDto).collect(toList());
              grid.setItems(ratingDtos);
            }
          });
    }
    if (productTitle != null) {
      h2.setText(productTitle);
    }
  }

  private RatingDto getRatingDto(Rating rating) {
    RatingDto ratingDto = new RatingDto();
    ratingDto.setComment(rating.getComment());
    ratingDto.setCreatedAt(rating.getCreatedAt());
    ratingDto.setRating(rating.getRating());
    ratingDto.setUpdatedAt(rating.getUpdatedAt());
    //    ratingDto.setUserId(rating.getUser().getId());
    //    ratingDto.setUserName(rating.getUser().getName());
    return ratingDto;
  }

  @Override
  public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
    Map<String, List<String>> queryParameters =
        event.getLocation().getQueryParameters().getParameters();
    List<String> productIdList = queryParameters.get("productId");
    List<String> productTitleList = queryParameters.get("productTitle");
    if (CollectionUtils.isEmpty(productIdList) || CollectionUtils.isEmpty(productTitleList)) {
      throw new RuntimeException("Invalid product details");
    }
    this.productId = Long.parseLong(productIdList.get(0));
    this.productTitle = productTitleList.get(0);
    updateList();
  }
}
