package com.xsis.master.crud.xsis_master_crud.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class VariantRequestDto {
  @NotBlank(message = "Name for product variant cannot be blank")
  @Size(max = 150, message = "Name for product variant cannot be more than 150 characters")
  private String name;

  @NotBlank(message = "Product slug for product variant cannot be blank")
  @Size(max = 175, message = "Product slug for product variant cannot be more than 175 characters")
  private String product;

  @NotBlank(message = "Product variant description cannot be blank")
  private String description;
  
  @Positive(message = "Product variant price must be more than 0")
  private Long price;

  @PositiveOrZero(message = "Product variant stock cannot be negative")
  private Long stock;
}
