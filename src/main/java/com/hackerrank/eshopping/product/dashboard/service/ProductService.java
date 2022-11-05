package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.dto.UpdateProductDTO;

public interface ProductService {
    public void addProduct(ProductDTO productDTO);

    public void updateProduct(Long productId, UpdateProductDTO updateProductDTO);

    public ProductDTO findProductById(Long productId);

    public ProductDTO[] findAllProducts();

    public ProductDTO[] findAllByCategory(String category);

    public ProductDTO[] findAllByCategoryAndAvailability(String category, boolean availability);
}
