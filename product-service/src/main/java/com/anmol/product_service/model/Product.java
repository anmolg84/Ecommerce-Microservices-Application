package com.anmol.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

// Product Class
// Define Product Model as MongoDB document
@Document(value = "product")
// Lombok annotations
@Data // Getters and Setters
@AllArgsConstructor // Parameterised Constructor
@NoArgsConstructor // Default Constructor
@Builder // Builds Final Class
public class Product {
    // Instance Variables
    @Id // Primary key
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
