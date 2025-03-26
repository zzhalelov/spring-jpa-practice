package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {
    // 1. проверка, что категория не создается из-за того что название занято
    @Test
    void create_shouldThrowException_whenCategoryNameAlreadyTaken() {
        // Подготовка
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryService categoryService = new CategoryService(categoryRepository);

        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingCategory.setName("Мебель");

        Mockito.when(categoryRepository.findByName(Mockito.anyString()))
                .thenReturn(Optional.of(existingCategory));

        Category category = new Category();
        category.setName("Мебель");

        // Исполнение и Проверка
        RuntimeException ex = assertThrows(RuntimeException.class, () -> categoryService.create(category));
        assertEquals("Категория с таким названием уже существует", ex.getMessage());
    }

    // 2. проверка, что категория создается
    @Test
    void create_shouldSave_whenCategoryNameIsNotTaken() {
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryService categoryService = new CategoryService(categoryRepository);

        Mockito.when(categoryRepository.findByName(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Category category = new Category();
        category.setName("Мебель");

        Category savedCategory = categoryService.create(category);

        assertEquals(category.getName(), savedCategory.getName());
    }
}