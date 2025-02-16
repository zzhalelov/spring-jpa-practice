package kz.zzhalelov.springjpa.controller;

import kz.zzhalelov.springjpa.model.*;
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
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDto(products);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable int id) {
        Product product = productRepository.findById(id).orElseThrow();
        return productMapper.toDto(product);
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
    public ProductDto create(@RequestBody ProductCreateDto productCreateDto) {
        Product product = productMapper.fromCreate(productCreateDto);
        Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow();
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable int id,
                          @RequestBody Product product) {
        Product existingProduct = productRepository
                .findById(id).orElseThrow();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productRepository.deleteById(id);
    }
}