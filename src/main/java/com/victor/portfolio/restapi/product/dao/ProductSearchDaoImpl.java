package com.victor.portfolio.restapi.product.dao;

import com.victor.portfolio.restapi.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchDaoImpl implements ProductSearchDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<ProductResponse> search(String nameLike,
                                        Double minPrice,
                                        Double maxPrice,
                                        Boolean active,
                                        Long categoryId) {

        String sql = """
            SELECT
              p.id            AS product_id,
              p.sku           AS sku,
              p.name          AS product_name,
              p.price         AS price,
              p.active        AS active,
              c.id            AS category_id,
              c.code          AS category_code,
              c.name          AS category_name
            FROM product p
            INNER JOIN product_category c ON c.id = p.category_id
            WHERE 1=1
              AND (:nameLike IS NULL OR LOWER(p.name) LIKE :nameLike)
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND (:active IS NULL OR p.active = :active)
              AND (:categoryId IS NULL OR c.id = :categoryId)
            ORDER BY p.id DESC
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nameLike", normalizeLike(nameLike));
        params.addValue("minPrice", minPrice);
        params.addValue("maxPrice", maxPrice);
        params.addValue("active", active);
        params.addValue("categoryId", categoryId);

        return jdbc.query(sql, params, (rs, rowNum) -> ProductResponse.builder()
                .id(rs.getLong("product_id"))
                .sku(rs.getString("sku"))
                .name(rs.getString("product_name"))
                .price(rs.getBigDecimal("price") != null ? rs.getBigDecimal("price").doubleValue() : null)
                .active(rs.getBoolean("active"))
                .categoryId(rs.getLong("category_id"))
                .categoryCode(rs.getString("category_code"))
                .categoryName(rs.getString("category_name"))
                .build()
        );
    }

    private String normalizeLike(String nameLike) {
        if (nameLike == null) return null;
        String trimmed = nameLike.trim().toLowerCase();
        if (trimmed.isEmpty()) return null;
        return "%" + trimmed + "%";
    }
}