package com.victor.portfolio.restapi.product.service;

import com.victor.portfolio.restapi.common.PagedResponse;
import com.victor.portfolio.restapi.exception.NotFoundException;
import com.victor.portfolio.restapi.product.domain.Product;
import com.victor.portfolio.restapi.product.domain.ProductCategory;
import com.victor.portfolio.restapi.product.dto.ProductCategoryDto;
import com.victor.portfolio.restapi.product.dto.ProductCreateRequest;
import com.victor.portfolio.restapi.product.dto.ProductResponse;
import com.victor.portfolio.restapi.product.repository.ProductCategoryRepository;
import com.victor.portfolio.restapi.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryDto> listCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> ProductCategoryDto.builder()
                        .id(c.getId())
                        .code(c.getCode())      // <-- ya existe en DTO
                        .name(c.getName())
                        .active(c.getActive())
                        .build())
                .toList();
    }

    @Override
    public PagedResponse<ProductResponse> listProducts(Pageable pageable) {

        Page<Product> page = productRepository.findAll(pageable);

        String sort = page.getSort().stream()
                .map(o -> o.getProperty() + ": " + o.getDirection())
                .collect(Collectors.joining(", "));

        return PagedResponse.<ProductResponse>builder()
                .items(page.getContent().stream().map(this::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .sort(sort.isBlank() ? null : sort)
                .build();
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + request.getCategoryId()));

        Product entity = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                // Entity: BigDecimal | Request: (probablemente) Double -> convertimos seguro
                .price(toBigDecimal(request.getPrice()))
                .active(request.getActive())
                .category(category)
                .build();

        Product saved = productRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .sku(p.getSku())
                .name(p.getName())
                // Entity: BigDecimal | Response: (probablemente) Double -> convertimos seguro
                .price(toDouble(p.getPrice()))
                .active(p.getActive())
                .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .build();
    }

    private BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    private Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }
}