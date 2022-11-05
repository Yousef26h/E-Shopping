package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.dto.UpdateProductDTO;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductAlreadyExistException;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductNotFoundException;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.addProduct(productDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ProductAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Object> updateProductById(@PathVariable("product_id") Long productId, @RequestBody UpdateProductDTO updateProductDTO) {
        try {
            productService.updateProduct(productId, updateProductDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("product_id") Long productId) {
        try {
            ProductDTO productById = productService.findProductById(productId);
            return new ResponseEntity<>(productById, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ProductDTO[] getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping(params = "category")
    public ProductDTO[] getAllProductsByCategory(@RequestParam String category) {
        return productService.findAllByCategory(category);
    }

    @GetMapping(params = {"category", "availability"})
    public ProductDTO[] getAllProductsByCategoryAndAvailability(@RequestParam String category, @RequestParam String availability) {
        if(availability.equals("1"))
            return productService.findAllByCategoryAndAvailability(category, true);

        return productService.findAllByCategoryAndAvailability(category, false);
    }
}
