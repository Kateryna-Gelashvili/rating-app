package org.k.ratingapp.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {
  private final MongoTemplate mongoTemplate;
  private final MongoConverter mongoConverter;

  public MongoConfig(MongoTemplate mongoTemplate, MongoConverter mongoConverter) {
    this.mongoTemplate = mongoTemplate;
    this.mongoConverter = mongoConverter;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void initIndicesAfterStartup() {
    var mappingContext = this.mongoConverter.getMappingContext();

    if (mappingContext instanceof MongoMappingContext) {
      MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
      for (BasicMongoPersistentEntity<?> persistentEntity :
          mongoMappingContext.getPersistentEntities()) {
        var clazz = persistentEntity.getType();
        if (clazz.isAnnotationPresent(Document.class)) {
          var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

          var indexOps = mongoTemplate.indexOps(clazz);
          resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
        }
      }
    }
  }
}
