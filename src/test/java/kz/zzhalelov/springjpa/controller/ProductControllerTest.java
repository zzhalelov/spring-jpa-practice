package kz.zzhalelov.springjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.model.ProductCreateDto;
import kz.zzhalelov.springjpa.model.ProductMapper;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;

@WebMvcTest({ProductController.class, ProductMapper.class})
public class ProductControllerTest {
    @MockitoBean
    ProductRepository productRepository;
    @MockitoBean
    CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_correctObjectGiven_shouldReturnCreated() {
        int productId = 1;
        int categoryId = 1;

        Category category = new Category();
        category.setName("Мебель");
        category.setId(categoryId);

        Mockito
                .when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(category));

        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setCategoryId(categoryId);
        productCreateDto.setName("Тумба");
        productCreateDto.setPrice(10_000.0);

        Mockito
                .when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocationOnMock -> {
                    Product product = invocationOnMock.getArgument(0, Product.class);
                    product.setId(productId);
                    return product;
                });

        String json = objectMapper.writeValueAsString(productCreateDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value(productCreateDto.getName()))
                .andExpect(jsonPath("$.price").value(productCreateDto.getPrice()))
                .andExpect(jsonPath("$.category").value(category.getName()));
    }

    @Test
    @SneakyThrows
    void findById_whenProductExists_shouldReturnOk() {
        int productId = 1;

        Category category = new Category();
        category.setId(1);
        category.setName("Smartphones");

        Product product = new Product();
        product.setId(1);
        product.setName("iPhone 16");
        product.setCategory(category);
        product.setPrice(400_000.0);

        Mockito
                .when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.category").value(product.getCategory().getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        Category category = new Category();
        category.setId(1);
        category.setName("Smartphones");

        Product product1 = new Product();
        product1.setId(1);
        product1.setName("iPhone");
        product1.setCategory(category);
        product1.setPrice(400_000.0);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Samsung Galaxy");
        product2.setCategory(category);
        product2.setPrice(450_000.0);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));
    }

    @Test
    @SneakyThrows
    void findById_whenProductDoesNotExists_shouldReturnNotFound() {
        int productId = 999;

        Mockito
                .when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isNotFound());
    }
}