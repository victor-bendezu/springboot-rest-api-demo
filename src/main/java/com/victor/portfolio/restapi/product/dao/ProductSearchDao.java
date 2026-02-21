package com.victor.portfolio.restapi.product.dao;

import com.victor.portfolio.restapi.product.dto.ProductResponse;

import java.util.List;

public interface ProductSearchDao {

    /**
     * Search products using an optimized JDBC query (JOIN + optional filters).
     * Any parameter can be null.
     */
    List<ProductResponse> search(String nameLike, Double minPrice, Double maxPrice, Boolean active, Long categoryId);
}