package com.xsis.master.crud.xsis_master_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.VariantRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.VariantResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.services.VariantService;
import com.xsis.master.crud.xsis_master_crud.validations.ValidSlug;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/variant")
public class VariantController {
  @Autowired
  private VariantService variantService;

  @GetMapping(
    path = "",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<List<VariantResponseDto>> getVariants(
      @RequestParam(defaultValue = "1")
      @Positive(message = "page argument must be more than 0") 
      Integer page,
      @RequestParam(defaultValue = "10") 
      @Positive(message = "limit argument must be more than 0") 
      Integer limit,
      @RequestParam(required = false)
      String product,
      @RequestParam(required = false)
      String category
  ) {
    if (product != null && category != null) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, 
        "Only product or category can be provided, but not both"
      );
    }
    return variantService.findVariants(product, category, page, limit);
  }
  
  @GetMapping(
    path = "/{slug}",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<VariantResponseDto> getVariantBySlug(@PathVariable("slug") @ValidSlug @Valid String slug) {
    return variantService.findVariantBySlug(slug);
  }

  @PostMapping(
    path = "",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> createNewVariant(@RequestBody @Valid VariantRequestDto variantRequstBody) {
    variantService.createNewVariant(variantRequstBody);
    return new WebResponse<String>("success", "Variant created successfully", null);
  }

  @PutMapping(
    name = "/{slug}",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> updateVariantBySlug(
    @PathVariable("slug") @ValidSlug String slug, 
    @RequestBody @Valid VariantRequestDto variantReqBody
  ) {
    variantService.updateVariantBySlug(variantReqBody, slug);
    return new WebResponse<String>("success", "Variant updated successfully", null);
  }

  @DeleteMapping(
    name = "/{slug}",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> deleteVariantBySlug(@PathVariable("slug") @ValidSlug String slug) {
    variantService.deleteVariantBySlug(slug);
    return new WebResponse<String>("success", "Variant deleted successfully", null);
  }
}

