package org.k.ratingapp.model;

import java.time.Instant;

public class Rating {
  private String userId;
  private Integer rating;
  private Instant createdAt;
  private Instant updatedAt;

  public String getUserId() {
    return userId;
  }

  public void setUser(String userId) {
    this.userId = userId;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
