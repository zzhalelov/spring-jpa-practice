package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new RuntimeException("название категории не может быть пустым");
        }

        Optional<Category> optional = categoryRepository.findByName(category.getName()); // Optional.empty
        if (optional.isPresent()) {
            throw new RuntimeException("Категория с таким названием уже существует");
        }

        categoryRepository.save(category);
        return category;
    }
}