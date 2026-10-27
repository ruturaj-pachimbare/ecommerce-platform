package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.entity.ProductStatus;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(
            ProductRequestDto request) {
        log.info(
                "Creating product : {}",
                request.getName());
        //Product product = new Product();
        Product product =
                productMapper.toEntity(request);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(
                request.getStockQuantity());
        product.setCategory(
                request.getCategory());

        product.setStatus(
                ProductStatus.ACTIVE);

        Product savedProduct =
                productRepository.save(product);

      //  return mapToResponse(savedProduct);

        return productMapper
                .toResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(
            Long productId) {
        log.info(
                "Fetching product id : {}",
                productId);
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id : "
                                                + productId));

        return mapToResponse(product);
    }

    @Override
    public ProductResponseDto updateProduct(
            Long productId,
            ProductRequestDto request) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id : "
                                                + productId));

        product.setName(request.getName());
        product.setDescription(
                request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(
                request.getStockQuantity());
        product.setCategory(
                request.getCategory());

        product = productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info(
                "Deleting product id : {}",
                productId);
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id : "
                                                + productId));

        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponseDto> getProducts(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return productRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public List<ProductResponseDto> searchProducts(
            String name) {

        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponseDto mapToResponse(
            Product product) {

        ProductResponseDto dto =
                new ProductResponseDto();

        dto.setProductId(
                product.getProductId());

        dto.setName(product.getName());
        dto.setDescription(
                product.getDescription());

        dto.setPrice(product.getPrice());

        dto.setStockQuantity(
                product.getStockQuantity());

        dto.setCategory(
                product.getCategory());

        dto.setStatus(
                product.getStatus().name());

        return dto;
    }
}