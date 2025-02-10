package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(double priceMin, double priceMax);
}