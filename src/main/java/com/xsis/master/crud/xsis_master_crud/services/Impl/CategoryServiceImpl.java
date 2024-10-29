package com.xsis.master.crud.xsis_master_crud.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.CategoryRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.CategoryResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.Pagination;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.repositories.CategoryRepository;
import com.xsis.master.crud.xsis_master_crud.services.CategoryService;
import com.xsis.master.crud.xsis_master_crud.utils.Slugify;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public WebResponse<List<CategoryResponseDto>> findAllCategories(Integer page, Integer limit) {
    Pageable paging = PageRequest.of(page - 1, limit, Sort.by(Sort.Order.asc("name")));
    Page<Object[]> categoriesResult = categoryRepository.findAllCategories(paging);

    if (categoriesResult.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories not found");
    }

    List<CategoryResponseDto> categories = categoriesResult.stream()
      .map(obj -> new CategoryResponseDto((String) obj[0], (String) obj[1]))
      .toList();
    WebResponse<List<CategoryResponseDto>> response = new WebResponse<List<CategoryResponseDto>>("success", "Categories retrieved successfully", categories);
    response.setPagination(new Pagination(page, limit, categoriesResult.getTotalPages()));
    return response;
  }

  @Override
  public WebResponse<CategoryResponseDto> findCategoryBySlug(String slug) {
    Object[] categoryResult = categoryRepository.findBySlug(slug);
    
    if (categoryResult.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with given name could not be found");
    }

    CategoryResponseDto category = new CategoryResponseDto(
      (String) ((Object[]) categoryResult[0])[0],
      (String) ((Object[]) categoryResult[0])[1]
    );
    return new WebResponse<>("success", "Category retrieved successfully", category);
  }

  @Override
  @Transactional
  public void createNewCategory(CategoryRequestDto category) {
    categoryRepository.insertCategory(category.getName(), Slugify.toSlug(category.getName()));
  }
}
