package com.anmol.product_service.controller;

import com.anmol.product_service.dto.ProductRequest;
import com.anmol.product_service.dto.ProductResponse;
import com.anmol.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // exposing RestApi
@RequestMapping("/api/product")
@RequiredArgsConstructor // Generate Required Constructor on Runtime
public class ProductController {
    private final ProductService productService;

    // Endpoint for Creating Products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

}
