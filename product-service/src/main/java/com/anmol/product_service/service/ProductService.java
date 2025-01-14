package com.anmol.product_service.service;

import com.anmol.product_service.dto.ProductRequest;
import com.anmol.product_service.dto.ProductResponse;
import com.anmol.product_service.model.Product;
import com.anmol.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Service Layer
@RequiredArgsConstructor // Generate Required Constructor on Runtime
@Slf4j // Logging By Lombok
public class ProductService {
    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        // Using Builder Method
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
        log.info("Product is {} Added",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }
    // Mapping Product To Product Response
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
