package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import com.project.compareproduct.backend.model.Product;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p.id FROM Product p")
    Optional<List<String>> findAllProductId();

    Product findByName(String name);
}
