package org.k.ratingapp.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.k.ratingapp.model.Product;
import org.k.ratingapp.service.ProductService;

@SpringComponent
@UIScope
public class ProductForm extends FormLayout {
  private final ProductService productService;

  private TextField title = new TextField("Title");
  private TextField description = new TextField("Description");
  private TextField id = new TextField("Id");
  private Button save = new Button("Save", VaadinIcon.CHECK.create());
  private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
  private Binder<Product> binder = new Binder<>(Product.class);
  private ChangeHandler changeHandler;

  public ProductForm(ProductService productService) {
    this.productService = productService;
    HorizontalLayout buttons = new HorizontalLayout(save, delete);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    add(title, description, buttons);
    binder
        .forField(id)
        .withNullRepresentation("")
        .withConverter(new StringToLongConverter("Must be Long"))
        .bind(Product::getId, Product::setId);
    binder.bindInstanceFields(this);
    save.addClickListener(event -> save());
    delete.addClickListener(event -> delete());
  }

  public void setProduct(Product product) {
    binder.setBean(product);

    if (product == null) {
      setVisible(false);
    } else {
      setVisible(true);
      title.focus();
    }
  }

  private void save() {
    Product product = binder.getBean();
    productService.save(product);
    setProduct(null);
    changeHandler.onChange();
  }

  private void delete() {
    Product product = binder.getBean();
    productService.delete(product.getId());
    setProduct(null);
    changeHandler.onChange();
  }

  public void setChangeHandler(ChangeHandler h) {
    changeHandler = h;
  }

  public interface ChangeHandler {
    void onChange();
  }
}
