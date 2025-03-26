package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Option;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.model.Value;
import kz.zzhalelov.springjpa.repository.OptionRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import kz.zzhalelov.springjpa.repository.ValueRepository;
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
class ValueServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    OptionRepository optionRepository;
    @Mock
    ValueRepository valueRepository;

    @InjectMocks
    ValueService valueService;

    @Test
    void create_IfProductExists() {
        int productId = 1;
        int optionId = 1;

        Category category = new Category();
        category.setId(1);
        category.setName("Мониторы");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setCategory(category);

        Option existingOption = new Option();
        existingOption.setId(optionId);
        existingOption.setName("Диагональ экрана");
        existingOption.setCategory(category);

        Mockito
                .when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingProduct));

        Mockito
                .when(optionRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingOption));

        Value value = valueService.create("16", productId, optionId);

        assertEquals("16", value.getName());
        assertEquals(productId, value.getProduct().getId());
        assertEquals(optionId, value.getOption().getId());
    }

    @Test
    void create_IfProductDoesNotExist() {
        int productId = 999;
        int optionId = 1;

        Mockito
                .when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> valueService.create("16", productId, optionId));
        assertEquals("Товар не найден", ex.getMessage());
    }

    @Test
    void create_IfOptionDoesNotExist() {
        int productId = 1;
        int optionId = 999;

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Mockito
                .when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingProduct));

        Mockito
                .when(optionRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> valueService.create("16", productId, optionId));
        assertEquals("Характеристика не найдена", ex.getMessage());
    }
}