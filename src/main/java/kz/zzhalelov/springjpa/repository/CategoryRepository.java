package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}