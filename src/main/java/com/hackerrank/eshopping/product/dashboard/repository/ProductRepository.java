package com.hackerrank.eshopping.product.dashboard.repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByCategoryOrderByAvailabilityDescDiscountedPriceAsc(String category);

    public List<Product> findAllByCategoryAndAvailability(String category, boolean availability);
}
