package kz.zzhalelov.springjpa.controller;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@RequestBody Category category,
                           @PathVariable int id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow();
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }
}