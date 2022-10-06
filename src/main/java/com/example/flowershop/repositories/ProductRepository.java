package com.example.flowershop.repositories;

import com.example.flowershop.models.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByStatus(int status);

    Optional<Product> findByDescription(String description);
}
