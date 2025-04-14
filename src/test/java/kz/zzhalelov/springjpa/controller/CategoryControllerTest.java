package kz.zzhalelov.springjpa.controller;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Option;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @MockitoBean
    CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findById_whenCategoryExists_shouldReturnOk() {
        int categoryId = 1;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Аудио");

        category.setOptions(List.of(
                Option.builder().id(1).name("Вес").category(category).build(),
                Option.builder().id(2).name("Мощность").category(category).build(),
                Option.builder().id(3).name("Производитель").category(category).build()

        ));

        Mockito
                .when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(category));

        mockMvc.perform(get("/categories/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath(("$.name")).value(category.getName()))
                .andExpect(jsonPath("$.length()").value(category.getOptions().size()))
                .andExpect(jsonPath("$.options[0].name").value("Вес"));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        Category firstCategory = new Category();
        firstCategory.setId(1);
        firstCategory.setName("ТВ");

        Category secondCategory = new Category();
        secondCategory.setId(2);
        secondCategory.setName("Приставки");

        Mockito
                .when(categoryRepository.findAll())
                .thenReturn(List.of(firstCategory, secondCategory));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(firstCategory.getId()))
                .andExpect(jsonPath("$[0].name").value(firstCategory.getName()))
                .andExpect(jsonPath("$[1].id").value(secondCategory.getId()))
                .andExpect(jsonPath("$[1].name").value(secondCategory.getName()));
    }
}