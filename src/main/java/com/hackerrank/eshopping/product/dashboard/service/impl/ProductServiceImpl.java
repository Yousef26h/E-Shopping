package com.hackerrank.eshopping.product.dashboard.service.impl;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.dto.UpdateProductDTO;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductAlreadyExistException;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductNotFoundException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import com.hackerrank.eshopping.product.dashboard.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        productRepository.findById(productDTO.getId())
                .ifPresent(
                        product -> {
                            throw new ProductAlreadyExistException("Product already exists");
                        });

        Product product = Mapper.convertProductDTOtoProduct(productDTO);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(Long productId, UpdateProductDTO updateProductDTO) {
        Product product = findById(productId);
        product.setRetailPrice(updateProductDTO.getRetail_price());
        product.setDiscountedPrice(updateProductDTO.getDiscounted_price());
        product.setAvailability(updateProductDTO.getAvailability());
        productRepository.save(product);
    }

    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = findById(productId);
        return Mapper.convertProductToProductDTO(product);
    }

    @Override
    public ProductDTO[] findAllProducts() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Product::getId))
                .map(Mapper::convertProductToProductDTO).toArray(ProductDTO[]::new);
    }

    @Override
    public ProductDTO[] findAllByCategory(String category) {
        return productRepository.findAllByCategoryOrderByAvailabilityDescDiscountedPriceAsc(category)
                .stream()
                .map(Mapper::convertProductToProductDTO).toArray(ProductDTO[]::new);
    }

    @Override
    public ProductDTO[] findAllByCategoryAndAvailability(String category, boolean availability) {
        String decodedCategory = decodeUrl(category);

        return productRepository.findAllByCategoryAndAvailability(decodedCategory, availability)
                .stream()
                .sorted(Comparator.comparing((Product product) ->
                                Math.round(((product.getRetailPrice() - product.getDiscountedPrice()) / product.getRetailPrice()) * 100), Comparator.reverseOrder())
                        .thenComparing(Product::getDiscountedPrice)
                        .thenComparing(Product::getId)
                )
                .map(Mapper::convertProductToProductDTO).toArray(ProductDTO[]::new);

    }

    private String decodeUrl(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    return new ProductNotFoundException("No product with id: " + productId);
                });
    }
}
