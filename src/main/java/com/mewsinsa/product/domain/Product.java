package com.mewsinsa.product.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {
  private Long productId;
  private String productName;
  private Long brandId;
  private String category;
  private String subcategory;
  private Long price;
  private Long like;
  private Long click;

  private String thumbnailImageUrl;

  //==Constructor ==//

  public Product(Long productId, String productName, Long brandId, String category, String subcategory,
      Long price, Long like, Long click, String thumbnailImageUrl) {
    this.productId = productId;
    this.productName = productName;
    this.brandId = brandId;
    this.category = category;
    this.subcategory = subcategory;
    this.price = price;
    this.like = like;
    this.click = click;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }

  public Product(String productName, Long brandId, String category, String subcategory,
      Long price, Long like, Long click, String thumbnailImageUrl) {
    this.productName = productName;
    this.brandId = brandId;
    this.category = category;
    this.subcategory = subcategory;
    this.price = price;
    this.like = like;
    this.click = click;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }


  //== Getter ==//
  public Long getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public Long getBrandId() {
    return brandId;
  }

  public String getCategory() {
    return category;
  }

  public String getSubcategory() {
    return subcategory;
  }

  public Long getPrice() {
    return price;
  }

  public Long getLike() {
    return like;
  }

  public Long getClick() {
    return click;
  }

  public String getThumbnailImageUrl() {
    return thumbnailImageUrl;
  }
}