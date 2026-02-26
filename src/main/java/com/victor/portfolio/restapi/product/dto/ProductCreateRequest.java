package com.victor.portfolio.restapi.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    @NotBlank(message = "SKU is required.")
    @Size(max = 20, message = "SKU must not exceed 20 characters.")
    private String sku;

    @NotBlank(message = "Name is required.")
    @Size(max = 120, message = "Name must not exceed 120 characters.")
    private String name;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0.")
    private BigDecimal price;

    @NotNull(message = "Category is required.")
    private Long categoryId;

    @NotNull(message = "Active is required.")
    private Boolean active;
}