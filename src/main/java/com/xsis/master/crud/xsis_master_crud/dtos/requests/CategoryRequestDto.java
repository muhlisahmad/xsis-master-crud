package com.xsis.master.crud.xsis_master_crud.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
  @NotBlank(message = "Name for category cannot be blank")
  @Size(max = 50, message = "Name for category cannot be more than 50 characters")
  private String name;
}
