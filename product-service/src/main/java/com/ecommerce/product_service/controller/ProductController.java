package com.ecommerce.product_service.controller;
import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import com.ecommerce.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {

        this.productService = productService;
    }

    @Operation(
            summary = "Create Product",
            description = "Creates a new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(
            @Valid
            @RequestBody
            ProductRequestDto request) {

        return productService
                .createProduct(request);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {

        return productService
                .getAllProducts();
    }

    @Operation(
            summary = "Get Product",
            description = "Fetch product by product id")
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(
            @PathVariable Long id) {

        return productService
                .getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(
            @PathVariable Long id,

            @Valid
            @RequestBody
            ProductRequestDto request) {

        return productService
                .updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);
    }

    @GetMapping("/page")
    public Page<ProductResponseDto> getProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return productService
                .getProducts(page, size);
    }

    @GetMapping("/search")
    public List<ProductResponseDto> searchProducts(

            @RequestParam String name) {

        return productService
                .searchProducts(name);
    }
}