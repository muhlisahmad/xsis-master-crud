package com.xsis.master.crud.xsis_master_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsis.master.crud.xsis_master_crud.dtos.requests.ProductRequestDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.ProductResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.services.ProductService;
import com.xsis.master.crud.xsis_master_crud.validations.ValidSlug;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping(
    path = "",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<List<ProductResponseDto>> getProducts(
      @RequestParam(defaultValue = "1")
      @Positive(message = "page argument must be more than 0") 
      Integer page,
      @RequestParam(defaultValue = "10") 
      @Positive(message = "limit argument must be more than 0") 
      Integer limit,
      @RequestParam(required = false)
      String category
  ) {
    return productService.findProducts(category, page, limit);
  }

  @GetMapping(
    path = "/{slug}",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<ProductResponseDto> getProductBySlug(@PathVariable("slug") @ValidSlug @Valid String slug) {
    return productService.findProductBySlug(slug);
  }

  @PostMapping(
    path = "",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> postMethodName(@RequestBody @Valid ProductRequestDto productReqBody) {
      productService.createNewProduct(productReqBody);
      return new WebResponse<String>("success", "Product created successfully", null);
  }

  @PutMapping(
    path = "/{slug}",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> updateProductBySlug(
    @PathVariable("slug") @ValidSlug String slug, 
    @RequestBody @Valid ProductRequestDto product
  ) {
    productService.updateProductBySlug(product, slug);
    return new WebResponse<String>("success", "Product updated successfully", null);
  }

  @DeleteMapping(
    path = "/{slug}",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public WebResponse<String> deleteProductBySlug(@PathVariable("slug") @ValidSlug String slug) {
    productService.deleteProductBySlug(slug);
    return new WebResponse<String>("success", "Product deleted successfully", null);
  }
}
