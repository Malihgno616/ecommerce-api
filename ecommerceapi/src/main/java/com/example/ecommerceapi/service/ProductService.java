package com.example.ecommerceapi.service;


import com.example.ecommerceapi.model.Products;
import com.example.ecommerceapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Products> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Products createProduct(Products product) {
        if(product.getProductName() != null) {
            product.setProductName(product.getProductName().trim());
        }

        return productRepository.save(product);
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product ID " + id + " not found."));
    }

    @Transactional
    public Products updateProduct(Long id, Products details) {
        Products existingProduct = getProductById(id);

        existingProduct.setProductName(details.getProductName());
        existingProduct.setPrice(details.getPrice());
        existingProduct.setDescription(details.getDescription());
        existingProduct.setQuantity(details.getQuantity());
        existingProduct.setImageUrl(details.getImageUrl());

        return productRepository.save(existingProduct);

    }

    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("this id dont exists");
        }

        productRepository.deleteById(id);
    }

}
