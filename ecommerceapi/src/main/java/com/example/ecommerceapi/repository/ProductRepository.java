package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Products, Long> {

    Page<Products> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    Page<Products> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Products> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}
