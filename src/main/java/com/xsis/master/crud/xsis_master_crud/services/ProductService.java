package com.xsis.master.crud.xsis_master_crud.services;

import java.util.List;

import com.xsis.master.crud.xsis_master_crud.dtos.responses.ProductResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;

public interface ProductService {
  WebResponse<List<ProductResponseDto>> findAllProducts(Integer page, Integer limit);
  WebResponse<ProductResponseDto> findProductBySlug(String slug);
}
