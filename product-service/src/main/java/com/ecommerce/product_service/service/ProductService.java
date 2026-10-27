package com.ecommerce.product_service.service;
import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(
            ProductRequestDto request);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(
            Long productId);

    ProductResponseDto updateProduct(
            Long productId,
            ProductRequestDto request);

    void deleteProduct(
            Long productId);

    Page<ProductResponseDto> getProducts(
            int page,
            int size);

    List<ProductResponseDto> searchProducts(
            String name);
}