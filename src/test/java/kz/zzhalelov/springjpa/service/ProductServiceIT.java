package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureTestDatabase
public class ProductServiceIT {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Test
    void deleteByIdTest() {
        Category category = new Category();
        category.setName("Laptops");
        categoryService.create(category);

        Product product = new Product();
        product.setName("test");

        Product savedProduct = productService.create(product, category.getId());

        productService.deleteById(savedProduct.getId());

        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> productService.findById(savedProduct.getId())
        );

        assertEquals("Товар не найден", ex.getMessage());
    }
}