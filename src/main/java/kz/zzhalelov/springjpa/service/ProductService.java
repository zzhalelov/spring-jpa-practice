package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product create(Product product, int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Категория не найдена"));
        product.setCategory(category);

        productRepository.save(product);
        return product;
    }

    public Product update(Product updatedProduct, int productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Товар не найден"));

        if (updatedProduct.getName() != null && !updatedProduct.getName().isBlank()) {
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        productRepository.save(existingProduct);
        return existingProduct;
    }

    public void deleteById(int productId) {
        productRepository.deleteById(productId);
    }

    public Product findById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Товар не найден"));
    }
}