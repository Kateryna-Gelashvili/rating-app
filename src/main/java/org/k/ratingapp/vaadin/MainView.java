package org.k.ratingapp.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.k.ratingapp.model.Product;
import org.k.ratingapp.service.ProductService;

import java.util.List;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {
  private final ProductService productService;
  private Grid<Product> grid = new Grid<>(Product.class);
  private TextField filterText = new TextField();

  public MainView(ProductService productService, ProductForm productForm) {
    productForm.setChangeHandler(
        () -> {
          productForm.setVisible(false);
          updateList();
        });

    productForm.setProduct(null);
    this.productService = productService;
    grid.setColumns("id", "title", "description", "createdAt", "updatedAt");
    grid.addComponentColumn(this::buildReviewsButton);
    updateList();
    grid.asSingleSelect()
        .addValueChangeListener(event -> productForm.setProduct(grid.asSingleSelect().getValue()));

    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.EAGER);
    filterText.addValueChangeListener(e -> updateList());

    Button addProductBtn = new Button("Add new product");
    addProductBtn.addClickListener(
        e -> {
          grid.asSingleSelect().clear();
          productForm.setProduct(new Product());
        });

    HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductBtn);

    HorizontalLayout mainContent = new HorizontalLayout(grid, productForm);
    mainContent.setSizeFull();

    add(toolbar, mainContent);
    setSizeFull();
  }

  private NativeButton buildReviewsButton(Product product) {
    NativeButton button = new NativeButton("View Reviews");
    button.addClickListener(
        e ->
            button
                .getUI()
                .ifPresent(
                    ui -> {
                      QueryParameters queryParameters = getQueryParameters(product);
                      ui.navigate("ratings", queryParameters);
                    }));
    return button;
  }

  private QueryParameters getQueryParameters(Product product) {
    return new QueryParameters(
        Map.of(
            "productId",
            List.of(String.valueOf(product.getId())),
            "productTitle",
            List.of(product.getTitle())));
  }

  public void updateList() {
    grid.setItems(productService.getProducts(filterText.getValue()));
  }
}
