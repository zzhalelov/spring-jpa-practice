package kz.zzhalelov.springjpa.controller;

import kz.zzhalelov.springjpa.model.Category;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.repository.CategoryRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping("/find-by-price-between")
    public List<Product> findByPriceBetween(@RequestParam double priceMin,
                                            @RequestParam double priceMax) {
        return productRepository.findByPriceBetween(priceMin, priceMax);
    }

    @GetMapping("/find-by-name-containing/{name}")
    public List<Product> findByNameContainingIgnoreCase(@PathVariable String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/find-with-max-price")
    public Optional<Product> findProductWithMaxPrice() {
        return productRepository.findTopByOrderByPriceDesc();
    }

    @PostMapping()
    public Product create(@RequestParam int categoryId,
                          @RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product);
    }
}