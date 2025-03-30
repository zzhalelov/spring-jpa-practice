package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    // 1. тест, где проверяете, что не создается товар из-за отсутствия категорий
    @Test
    void create_shouldThrowException_WhenCategoryIsNotExists() {
        int categoryId = 999;

        Mockito.when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Product product = new Product();
        product.setName("Мебель");
        product.setPrice(10000.0);

        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> productService.create(product, categoryId));
        assertEquals("Категория не найдена", ex.getMessage());
    }

    // 2. тест, где проверяете, что создается товар
    @Test
    void create_IfCategoryExists() {
        int categoryId = 1;

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Мебель");

        Mockito.when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingCategory));

        Product product = new Product();
        product.setName("Мебель");
        product.setPrice(10000.0);

        Product savedProduct = productService.create(product, categoryId);

        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(existingCategory.getId(), savedProduct.getCategory().getId());
        assertEquals(existingCategory.getName(), savedProduct.getCategory().getName());
    }

    // Тесты:
    // 1. Проверка, на обновление название товара, убедитесь что, если передаем товар только с названием,
    //    у существующего товара меняется только название, а стоимость и категория остается прежним
    @Test
    void update_UpdateName_WhenNameIsExists() {
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Старое название");
        existingProduct.setPrice(1000.0);

        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingProduct.setCategory(existingCategory);

        Product updProduct = new Product();
        updProduct.setName("Новое название");

        Mockito
                .when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        Product product = productService.update(updProduct, productId);
        assertEquals("Новое название", product.getName());
        assertEquals(1000.0, product.getPrice());
        assertEquals(1, product.getCategory().getId());
    }

    // 2. Проверка, на обновление стоимости товара, убедитесь что, если передаем товар только со стоимостью,
    //    у существующего товара меняется только стоимость, а название и категория остается прежним
    @Test
    void update_UpdatePrice_WhenPriceIsExists() {
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Название");
        existingProduct.setPrice(1000.0);

        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingProduct.setCategory(existingCategory);

        Product updProduct = new Product();
        updProduct.setPrice(2000.0);

        Mockito
                .when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        Product product = productService.update(updProduct, productId);
        assertEquals("Название", product.getName());
        assertEquals(2000.0, product.getPrice());
        assertEquals(1, product.getCategory().getId());
    }

    // 3. Проверка, на обновление стоимости и названия товара, убедитесь что, если передаем товар со стоимостью и с названием,
    //    у существующего товара меняется стоимость и название, а категория остается прежним
    @Test
    void update_UpdateNameAndPrice_WhenNameAndPriceIsExists() {
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Старое Название");
        existingProduct.setPrice(1000.0);

        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingProduct.setCategory(existingCategory);

        Product updProduct = new Product();
        updProduct.setName("Новое Название");
        updProduct.setPrice(2000.0);

        Mockito
                .when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        Product product = productService.update(updProduct, productId);
        assertEquals("Новое Название", product.getName());
        assertEquals(2000.0, product.getPrice());
        assertEquals(1, product.getCategory().getId());
    }

    // 4. Проверка, на обновление не существующего товара, должно выйти исключение
    @Test
    void update_shouldThrowException_IfProductDoesNotExist() {
        int productId = 9999;
        Product updatedProduct = new Product();
        updatedProduct.setName("Новое Название");
        updatedProduct.setPrice(2000.0);

        Mockito
                .when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> productService.update(updatedProduct, productId));
        assertEquals("Товар не найден", ex.getMessage());
    }
}