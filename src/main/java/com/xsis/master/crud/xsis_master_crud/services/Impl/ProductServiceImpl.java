package com.xsis.master.crud.xsis_master_crud.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xsis.master.crud.xsis_master_crud.dtos.responses.Pagination;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.ProductResponseDto;
import com.xsis.master.crud.xsis_master_crud.dtos.responses.WebResponse;
import com.xsis.master.crud.xsis_master_crud.repositories.ProductRepository;
import com.xsis.master.crud.xsis_master_crud.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

  @Autowired
  private ProductRepository productRepository;

  @Override
  public WebResponse<List<ProductResponseDto>> findAllProducts(Integer page, Integer limit) {
    Pageable paging = PageRequest.of(page - 1, limit, Sort.by(Sort.Order.asc("name")));
    Page<Object[]> productsResult = productRepository.findAllProducts(paging);

    if (productsResult.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found");
    }

    List<ProductResponseDto> products = productsResult.stream()
      .map(obj -> new ProductResponseDto((String) obj[0], (String) obj[1], (String) obj[2]))
      .toList();
    WebResponse<List<ProductResponseDto>> response = new WebResponse<List<ProductResponseDto>>("success", "Products retrieved successfully", products);
    response.setPagination(new Pagination(page, limit, productsResult.getTotalPages()));
    return response;
  }

  @Override
  public WebResponse<ProductResponseDto> findProductBySlug(String slug) {
    Object[] productResult = productRepository.findBySlug(slug);

    if (productResult.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given name could not be found");
    }

    ProductResponseDto product = new ProductResponseDto(
      (String) ((Object[]) productResult[0])[0], 
      (String) ((Object[]) productResult[0])[1], 
      (String) ((Object[]) productResult[0])[2]
    );

    return new WebResponse<ProductResponseDto>("success", "Product retrieved successfully", product);
  }
}
