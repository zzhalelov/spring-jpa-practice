package kz.zzhalelov.springjpa.repository;

import jakarta.persistence.EntityManager;
import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Option;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.model.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ValueRepository valueRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void create_shouldCreate_WhenCategoryExists() {
        Category existingCategory = new Category();
        existingCategory.setName("Мебель");
        categoryRepository.save(existingCategory);

        Product product = new Product();
        product.setName("Тумба");
        product.setPrice(10_000.0);
        product.setCategory(existingCategory);

        Product savedProduct = productRepository.save(product);

        Assertions.assertEquals(product.getName(), savedProduct.getName());
        Assertions.assertEquals(product.getCategory(), savedProduct.getCategory());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());

        System.out.println(product.getCategory());
        System.out.println(savedProduct.getCategory());
    }

    @Test
    void find_betweenByPrice() {
        Category existingCategory1 = new Category();
        existingCategory1.setName("Мебель");
        categoryRepository.save(existingCategory1);

        Product product1 = new Product();
        product1.setName("Тумба");
        product1.setPrice(10_000.0);
        product1.setCategory(existingCategory1);
        productRepository.save(product1);

        Category existingCategory2 = new Category();
        existingCategory2.setName("Техника");
        categoryRepository.save(existingCategory2);

        Product product2 = new Product();
        product2.setName("Миксер");
        product2.setPrice(100_000.0);
        product2.setCategory(existingCategory2);
        productRepository.save(product2);

        List<Product> products = productRepository.findByPriceBetween(5_000.0, 90_000);

        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(product1.getPrice(), products.get(0).getPrice());
    }

    @Test
    void create_shouldCreateCategoryWithTwoOptions() {
        Category category = new Category();
        category.setName("Smartphones");
        categoryRepository.save(category);

        Option option1 = new Option();
        option1.setName("Color");
        option1.setCategory(category);
        optionRepository.save(option1);

        Option option2 = new Option();
        option2.setName("Storage");
        option2.setCategory(category);
        optionRepository.save(option2);

        entityManager.clear();

        Category savedCategory = categoryRepository.findById(category.getId()).orElseThrow();

        Assertions.assertEquals(2, savedCategory.getOptions().size());
    }

    @Test
    void create_shouldCreateProductAndCheckThatOptionsRelatesToProductOptions() {
        Category category = new Category();
        category.setName("Smartphones");
        categoryRepository.save(category);

        Option option1 = new Option();
        option1.setName("Color");
        option1.setCategory(category);
        optionRepository.save(option1);

        Option option2 = new Option();
        option2.setName("Storage");
        option2.setCategory(category);
        optionRepository.save(option2);

        Product product = new Product();
        product.setName("iPhone 15");
        product.setCategory(category);
        product.setPrice(500_000.0);
        productRepository.save(product);

        Value value1 = new Value();
        value1.setName("Black");
        value1.setOption(option1);
        value1.setProduct(product);
        valueRepository.save(value1);

        Value value2 = new Value();
        value2.setName("128GB");
        value2.setOption(option2);
        value2.setProduct(product);
        valueRepository.save(value2);

        entityManager.clear();

        Product savedProduct = productRepository.findById(product.getId()).orElseThrow();
        Category savedCategory = categoryRepository.findById(category.getId()).orElseThrow();

        Assertions.assertEquals(2, savedProduct.getValues().size());
        Assertions.assertEquals("Black", savedProduct.getValues().get(0).getName());
        Assertions.assertEquals("128GB", savedProduct.getValues().get(1).getName());

        Assertions.assertEquals(2, savedCategory.getOptions().size());
        Assertions.assertEquals("Color", savedCategory.getOptions().get(0).getName());
        Assertions.assertEquals("Storage", savedCategory.getOptions().get(1).getName());
    }

    @Test
    void find_findProductByValueName() {
        Category category1 = new Category();
        category1.setName("SmartPhones");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Laptops");
        categoryRepository.save(category2);

        Option optionManufacturerForCategory1 = new Option();
        optionManufacturerForCategory1.setName("Manufacturer");
        optionManufacturerForCategory1.setCategory(category1);
        optionRepository.save(optionManufacturerForCategory1);

        Option optionStorageForCategory1 = new Option();
        optionStorageForCategory1.setName("Storage");
        optionStorageForCategory1.setCategory(category1);
        optionRepository.save(optionStorageForCategory1);

        Option optionManufacturerForCategory2 = new Option();
        optionManufacturerForCategory2.setName("Manufacturer");
        optionManufacturerForCategory2.setCategory(category2);
        optionRepository.save(optionManufacturerForCategory2);

        Option optionStorageForCategory2 = new Option();
        optionStorageForCategory2.setName("Storage");
        optionStorageForCategory2.setCategory(category2);
        optionRepository.save(optionStorageForCategory2);

        Product product1 = new Product();
        product1.setName("iPhone 12");
        product1.setCategory(category1);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("MacBook Pro 14");
        product2.setCategory(category2);
        productRepository.save(product2);

        Value value1 = new Value();
        value1.setProduct(product1);
        value1.setOption(optionManufacturerForCategory1);
        value1.setName("Apple");
        valueRepository.save(value1);

        Value value2 = new Value();
        value2.setProduct(product1);
        value2.setOption(optionStorageForCategory1);
        value2.setName("128GB");
        valueRepository.save(value2);

        Value value3 = new Value();
        value3.setProduct(product2);
        value3.setOption(optionManufacturerForCategory2);
        value3.setName("Apple");
        valueRepository.save(value3);

        Value value4 = new Value();
        value4.setProduct(product2);
        value4.setOption(optionStorageForCategory2);
        value4.setName("512GB");
        valueRepository.save(value4);

        entityManager.clear();

        List<Product> productList = productRepository.findByValueName("Apple");

        Assertions.assertEquals("iPhone 12", productList.get(0).getName());
        Assertions.assertEquals("MacBook Pro 14", productList.get(1).getName());

        List<Product> products = productRepository.findByValueName("Apple");

        Assertions.assertEquals("MacBook Pro 14", products.get(1).getName());
    }
}