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
public class ProductCategoryDto {

    private Long id;
    private String code;     // <-- IMPORTANTE: esto lo usa el ServiceImpl
    private String name;
    private Boolean active;
}