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

import com.xsis.master.crud.xsis_master_crud.dtos.requests.VariantRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.Pagination;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.VariantResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.repositories.ProductRepository;
import com.xsis.master.crud.xsis_master_crud.repositories.VariantRepository;
import com.xsis.master.crud.xsis_master_crud.services.VariantService;
import com.xsis.master.crud.xsis_master_crud.utils.Slugify;

import jakarta.validation.Validator;

@Service
public class VariantServiceImpl implements VariantService {
  @Autowired
  private Validator validator;

  @Autowired
  private VariantRepository variantRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public WebResponse<List<VariantResponseDto>> findVariants(String product, String category, Integer page, Integer limit) {
    Pageable paging = PageRequest.of(page - 1, limit, Sort.by(Sort.Order.asc("name")));
    Page<Object[]> variantsResult = null;
    
    if (product != null) {
      variantsResult = variantRepository.findVariantsByProduct(Slugify.validateSlug(product, validator), paging);
    } else if (category != null) {
      variantsResult = variantRepository.findVariantsByCategory(Slugify.validateSlug(category, validator), paging);
    } else {
      variantsResult = variantRepository.findAllVariants(paging);
    }

    if (variantsResult.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variants not found");
    }

    List<VariantResponseDto> variants = variantsResult.stream()
      .map(obj -> new VariantResponseDto(
        (String) obj[0], 
        (String) obj[1], 
        (String) obj[2], 
        (String) obj[3],
        (String) obj[4],
        (Long) obj[5],
        (Long) obj[6])
      )
      .toList();

    WebResponse<List<VariantResponseDto>> response = new WebResponse<List<VariantResponseDto>>("success", "Variants retrieved successfully", variants);
    response.setPagination(new Pagination(page, limit, variantsResult.getTotalPages()));
    return response;
  }

  @Override
  public WebResponse<VariantResponseDto> findVariantBySlug(String slug) {
    Object[] variantResult = variantRepository.findBySlug(slug);

    if (variantResult.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant with given name could not be found");
    }

    VariantResponseDto variant = new VariantResponseDto(
      (String) ((Object[]) variantResult[0])[0],
      (String) ((Object[]) variantResult[0])[1],
      (String) ((Object[]) variantResult[0])[2],
      (String) ((Object[]) variantResult[0])[3],
      (String) ((Object[]) variantResult[0])[4],
      (Long) ((Object[]) variantResult[0])[5],
      (Long) ((Object[]) variantResult[0])[6]
    );
    return new WebResponse<VariantResponseDto>("success", "Variant retrieved successfully", variant);
  }

  @Override
  @Transactional
  public void createNewVariant(VariantRequestDto variant) {
    Object[] product = productRepository.findBySlug(variant.getProduct());

    if (product.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No product with given name");
    }

    variantRepository.insertVariant(
      variant.getName(), 
      Slugify.toSlug(variant.getName()), 
      variant.getProduct(), 
      variant.getDescription(), 
      variant.getPrice(), 
      variant.getStock()
    );
  }

  @Override
  @Transactional
  public void updateVariantBySlug(VariantRequestDto variant, String slug) {
    Object[] checkVariant = variantRepository.findBySlug(slug);
    Object[] checkProduct = productRepository.findBySlug(variant.getProduct());

    if (checkVariant.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant with given name could not be found");
    }

    if (checkProduct.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given name could not be found");
    }

    variantRepository.updateVariantBySlug(
      variant.getName(), Slugify.toSlug(variant.getName()), 
      variant.getProduct(), variant.getDescription(), 
      variant.getPrice(), variant.getStock(), slug
    );
  }

  @Override
  @Transactional
  public void deleteVariantBySlug(String slug) {
    Object[] checkVariant = variantRepository.findBySlug(slug);

    if (checkVariant.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No variant with given name");
    }

    variantRepository.deleteVariantBySlug(slug);
  }
}
