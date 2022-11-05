package com.hackerrank.eshopping.product.dashboard.utils;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.model.Product;


public class Mapper {

    public static ProductDTO convertProductToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .retail_price(product.getRetailPrice())
                .discounted_price(product.getDiscountedPrice())
                .availability(product.getAvailability())
                .build();
    }

    public static Product convertProductDTOtoProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .category(productDTO.getCategory())
                .retailPrice(productDTO.getRetail_price())
                .discountedPrice(productDTO.getDiscounted_price())
                .availability(productDTO.getAvailability())
                .build();
    }
}
