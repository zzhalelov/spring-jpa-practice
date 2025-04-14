package kz.zzhalelov.springjpa.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findById_whenCategoryExists_shouldReturnOk() {
        int categoryId = 1;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/" + categoryId));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Процессоры"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.options.length()").value(3));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Процессоры"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Мониторы"));
    }
}