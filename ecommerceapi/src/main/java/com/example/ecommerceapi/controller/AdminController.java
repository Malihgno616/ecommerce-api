package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.ProductRequestDTO;
import com.example.ecommerceapi.model.Products;
import com.example.ecommerceapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> store(@RequestBody ProductRequestDTO data) {
        Products product = new Products();

        product.setProductName(data.getProductName());
        product.setPrice(data.getPrice());
        product.setDescription(data.getDescription());
        product.setQuantity(data.getQuantity());
        product.setImageUrl(data.getImageUrl());

        Products newProduct = productService.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);

    }

}
