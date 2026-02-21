package com.victor.portfolio.restapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String sku;
    private String name;
    private Double price;

    private Boolean active;

    private Long categoryId;

    // 👇 ESTOS DOS CAMPOS SON LOS QUE FALTABAN
    private String categoryCode;
    private String categoryName;
}