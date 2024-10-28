package com.xsis.master.crud.xsis_master_crud.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VariantResponseDto {
  private String name;
  private String slug;
  private String product;
  private String category;
  private String description;
  private Long stock;
  private Long price;
}
