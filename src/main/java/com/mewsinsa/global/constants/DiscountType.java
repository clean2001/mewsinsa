package com.mewsinsa.global.constants;

public enum DiscountType {
  FIXED_AMOUNT("amount"),
  FIXED_RATE("rate");

  private String type;
  DiscountType(String type) {
    this.type = type;
  }
}
