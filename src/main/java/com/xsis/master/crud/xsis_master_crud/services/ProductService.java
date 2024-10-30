package com.xsis.master.crud.xsis_master_crud.services;

import java.util.List;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.ProductRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.ProductResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;

public interface ProductService {
  WebResponse<List<ProductResponseDto>> findProducts(String product, Integer page, Integer limit);
  WebResponse<ProductResponseDto> findProductBySlug(String slug);
  void createNewProduct(ProductRequestDto product);
  // WebResponse<List<ProductResponseDto>> findProductsByCategory(String product, Integer page, Integer limit);
}
