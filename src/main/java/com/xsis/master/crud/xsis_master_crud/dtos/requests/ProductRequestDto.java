package com.xsis.master.crud.xsis_master_crud.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {
  @NotBlank(message = "Name for product cannot be blank")
  @Size(max = 50, message = "Name for product cannot be more than 50 characters")
  private String name;
  
  @NotBlank(message = "Category slug for product cannot be blank")
  @Size(max = 75 ,message = "Category slug for product cannot be more than 75 characters")
  private String category;
}
