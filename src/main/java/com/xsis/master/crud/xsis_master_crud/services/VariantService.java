package com.xsis.master.crud.xsis_master_crud.services;

import java.util.List;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.VariantRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.VariantResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;

public interface VariantService {
  WebResponse<List<VariantResponseDto>> findAllVariants(Integer page, Integer limit);
  WebResponse<VariantResponseDto> findVariantBySlug(String slug);
  void createNewVariant(VariantRequestDto variant);
}
