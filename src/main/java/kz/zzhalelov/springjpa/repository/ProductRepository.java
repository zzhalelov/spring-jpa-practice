package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}