package org.k.ratingapp.event;

import org.k.ratingapp.model.Product;
import org.k.ratingapp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductModelListener extends AbstractMongoEventListener<Product> {

  private SequenceGeneratorService sequenceGenerator;

  @Autowired
  public ProductModelListener(SequenceGeneratorService sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Product> event) {
    if (event.getSource().getId() == null) {
      event.getSource().setId(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME));
    }
  }
}
