package com.hackerrank.eshopping.product.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private Double retail_price;
    private Double discounted_price;
    private Boolean availability;
}
