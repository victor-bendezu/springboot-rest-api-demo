package com.victor.portfolio.restapi.product.service;

import com.victor.portfolio.restapi.common.PagedResponse;
import com.victor.portfolio.restapi.product.dto.ProductCategoryDto;
import com.victor.portfolio.restapi.product.dto.ProductCreateRequest;
import com.victor.portfolio.restapi.product.dto.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductCategoryDto> listCategories();

    PagedResponse<ProductResponse> listProducts(Pageable pageable);

    ProductResponse createProduct(ProductCreateRequest request);

    void deleteProduct(Long id);
}