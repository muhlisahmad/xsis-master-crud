package com.xsis.master.crud.xsis_master_crud.services;

import java.util.List;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.CategoryRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.CategoryResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;

public interface CategoryService {
  WebResponse<List<CategoryResponseDto>> findCategories(Integer page, Integer limit);
  WebResponse<CategoryResponseDto> findCategoryBySlug(String slug);
  void createNewCategory(CategoryRequestDto category);
  void updateCategoryBySlug(CategoryRequestDto category, String slug);
  void deleteCategoryBySlug(String slug);
}