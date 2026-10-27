package com.ecommerce.product_service.service;
import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.entity.ProductStatus;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

  /*  @Test
    void shouldGetProductById() {

        Product product = new Product();

        product.setProductId(1L);
        product.setName("iPhone 16");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setStatus(ProductStatus.ACTIVE);
        ProductResponseDto response =
                new ProductResponseDto();

        response.setProductId(1L);
        response.setName("iPhone 16");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productMapper.toResponseDto(product))
                .thenReturn(response);

        ProductResponseDto result =
                productService.getProductById(1L);

        assertEquals(
                "iPhone 16",
                result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(1L)
        );
    }


    @Test
    void shouldCreateProduct() {

        ProductRequestDto request =
                new ProductRequestDto();

        request.setName("iPhone");
        request.setDescription("Apple Phone");
        request.setPrice(
                BigDecimal.valueOf(100000));

        request.setStockQuantity(10);
        request.setCategory("MOBILE");

        Product product = new Product();

        product.setProductId(1L);
        product.setName("iPhone");
        product.setStatus(ProductStatus.ACTIVE);

      *//*  when(productRepository.save(any(Product.class)))
                .thenReturn(product);*//*

        ProductResponseDto response =
                productService.createProduct(request);

        assertEquals(
                "iPhone",
                response.getName());
    }*/
}
