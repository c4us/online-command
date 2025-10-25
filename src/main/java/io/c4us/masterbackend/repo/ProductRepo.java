package io.c4us.masterbackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Product;

public interface ProductRepo extends JpaRepository<Product, String>{
    @SuppressWarnings("null")
    Optional<Product> findById(String id);
    List<Product> findByCategoryId(String idCat);
    long countByCategoryId(String categoryId);

    
}
