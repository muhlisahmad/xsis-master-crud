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
import com.xsis.master.crud.xsis_master_crud.repositories.ProductRepository;
import com.xsis.master.crud.xsis_master_crud.repositories.VariantRepository;
import com.xsis.master.crud.xsis_master_crud.services.CategoryService;
import com.xsis.master.crud.xsis_master_crud.utils.Slugify;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private VariantRepository variantRepository;

  @Override
  public WebResponse<List<CategoryResponseDto>> findCategories(Integer page, Integer limit) {
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

  @Override
  @Transactional
  public void updateCategoryBySlug(CategoryRequestDto category, String slug) {
    Object[] checkCategory = categoryRepository.findBySlug(slug);
    
    if (checkCategory.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with given name could not be found");
    }

    categoryRepository.updateCategoryBySlug(category.getName(), Slugify.toSlug(category.getName()), slug);
  }

  @Override
  @Transactional
  public void deleteCategoryBySlug(String slug) {
    Object[] checkCategory = categoryRepository.findBySlug(slug);

    if (checkCategory.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category with given name");
    }
    Integer productPage = 0;
    Integer productTotalPage = 0;
    Integer variantPage = 0;
    Integer variantTotalPage = 0;

    while (productPage != productTotalPage || (productPage == 0 && productTotalPage == 0)) {
      Pageable paging = PageRequest.of(productPage, 10, Sort.by(Sort.Order.asc("name")));
      Page<Object[]> productsCategory = productRepository.findProductsByCategory(slug, paging);

      if (productsCategory.isEmpty()) {
        break;
      }
      
      productsCategory.stream().forEach(obj -> {
        productRepository.deleteProductBySlug((String) obj[1]);
      });
      
      productPage ++;
      productTotalPage = productsCategory.getTotalPages();
    }

    while (variantPage != variantTotalPage || (variantPage == 0 && variantTotalPage == 0)) {
      Pageable paging = PageRequest.of(variantPage, 10, Sort.by(Sort.Order.asc("name")));
      Page<Object[]> variantsCategory = variantRepository.findVariantsByCategory(slug, paging);

      if (variantsCategory.isEmpty()) {
        break;
      }
      
      variantsCategory.stream().forEach(obj -> {
        variantRepository.deleteVariantBySlug((String) obj[1]);
      });
      
      variantPage ++;
      variantTotalPage = variantsCategory.getTotalPages();
    }

    categoryRepository.deleteCategoryBySlug(slug);
  }
}
