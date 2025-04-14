package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByName_shouldReturnCategory() {
        Category category = new Category();
        category.setName("Мебель");
        categoryRepository.save(category);

        Optional<Category> optional = categoryRepository.findByName("Мебель");

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(category.getName(), optional.get().getName());
    }

    @Test
    void findByNameIgnoreCase_shouldReturnNonEmptyList() {
        Category category1 = new Category();
        category1.setName("Техника");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Электроника");
        categoryRepository.save(category2);

        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase("ка");

        assertThat(categories, hasSize(2));
        assertThat(categories, contains(
                allOf(
                        hasProperty("id"),
                        hasProperty("name", equalTo("Техника")),
                        hasProperty("products", empty())
                ),
                allOf(
                        hasProperty("id"),
                        hasProperty("name", equalTo("Электроника")),
                        hasProperty("products", empty())
                )
        ));

        Assertions.assertEquals(2, categories.size());
        Assertions.assertEquals(category1.getName(), categories.get(0).getName());
        Assertions.assertEquals(category2.getName(), categories.get(1).getName());
    }
}