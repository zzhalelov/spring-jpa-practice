package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(double priceMin, double priceMax);

    List<Product> findByNameContainingIgnoreCase(String name);

    Optional<Product> findTopByOrderByPriceDesc();
}