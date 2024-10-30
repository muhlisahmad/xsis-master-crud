package com.xsis.master.crud.xsis_master_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.CategoryRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.CategoryResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.services.CategoryService;
import com.xsis.master.crud.xsis_master_crud.validations.ValidSlug;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @GetMapping(
    path = "",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<List<CategoryResponseDto>> getCategories(
      @RequestParam(defaultValue = "1")
      @Positive(message = "page argument must be more than 0") 
      Integer page,
      @RequestParam(defaultValue = "10") 
      @Positive(message = "limit argument must be more than 0") 
      Integer limit
    ) {
      return categoryService.findCategories(page, limit);
  }
  
  @GetMapping(
    path = "/{slug}",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<CategoryResponseDto> getCategoryBySlug(@PathVariable("slug") @ValidSlug String slug) {
    return categoryService.findCategoryBySlug(slug);
  }

  @PostMapping(
    path = "",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> createNewCategory(@RequestBody @Valid CategoryRequestDto categoryRequestBody) {
    categoryService.createNewCategory(categoryRequestBody);
    return new WebResponse<String>("success", "Category created successfully", null);
  }

  @PutMapping(
    path = "/{slug}",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> updateCategoryBySlug(
    @PathVariable("slug") 
    @ValidSlug String 
    slug, 
    @RequestBody 
    @Valid CategoryRequestDto 
    categoryReqBody
  ) {
    categoryService.updateCategoryBySlug(categoryReqBody, slug);
    return new WebResponse<String>("success", "Category updated successfully", null);
  }
}
