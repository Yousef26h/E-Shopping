package com.hackerrank.eshopping.product.dashboard.dto;

import lombok.Data;

@Data
public class UpdateProductDTO {
    private Double retail_price;
    private Double discounted_price;
    private Boolean availability;
}

