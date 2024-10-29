package com.xsis.master.crud.xsis_master_crud.dtos.requests;

import com.xsis.master.crud.xsis_master_crud.validations.ValidSlug;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
  @NotBlank(message = "Name for product cannot be blank")
  @Size(max = 50, message = "Name for product cannot be more than 50 characters")
  private String name;
  
  @NotBlank(message = "Category slug for product cannot be blank")
  @Size(max = 75 ,message = "Category slug for product cannot be more than 75 characters")
  @ValidSlug
  private String category;
}
