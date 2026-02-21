package com.victor.portfolio.restapi.product.controller;

import com.victor.portfolio.restapi.common.ApiResponse;
import com.victor.portfolio.restapi.common.PagedResponse;
import com.victor.portfolio.restapi.product.dto.ProductCategoryDto;
import com.victor.portfolio.restapi.product.dto.ProductCreateRequest;
import com.victor.portfolio.restapi.product.dto.ProductResponse;
import com.victor.portfolio.restapi.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> listCategories() {
        List<ProductCategoryDto> data = productService.listCategories();
        return ResponseEntity.ok(ApiResponse.ok("OK", data));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> listProducts(
            @ParameterObject Pageable pageable
    ) {
        PagedResponse<ProductResponse> data = productService.listProducts(pageable);
        return ResponseEntity.ok(ApiResponse.ok("OK", data));
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse saved = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.ok("Created", saved));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.ok("Deleted"));
    }
}