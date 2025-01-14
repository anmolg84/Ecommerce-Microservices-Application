package com.anmol.product_service.repository;

import com.anmol.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Store Product in DB
@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
}
