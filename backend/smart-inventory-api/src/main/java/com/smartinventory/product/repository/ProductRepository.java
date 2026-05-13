package com.smartinventory.product.repository;

import com.smartinventory.product.model.Product;
import com.smartinventory.product.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    boolean existsBySku(String sku);
    List<Product> findByStatusOrderByNameAsc(ProductStatus status);
    List<Product> findByCurrentStockLessThanEqual(Integer stock);
}
