package kz.zzhalelov.springjpa.controller;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.CategoryDto;
import kz.zzhalelov.springjpa.model.CategoryFullDto;
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
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::of)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryFullDto findById(@PathVariable int id) {
        return categoryRepository.findById(id)
                .map(CategoryFullDto::of)
                .orElseThrow();
    }

    @GetMapping("/find-by-name/{name}")
    public Category findByName(@PathVariable String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElseThrow();
    }

    @GetMapping("/find-by-name-containing/{name}")
    public List<Category> findByNameContaining(@PathVariable String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
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