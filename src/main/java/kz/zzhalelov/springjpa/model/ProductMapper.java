package kz.zzhalelov.springjpa.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public Product fromCreate(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());

        Category category = new Category();
        category.setId(productCreateDto.getCategoryId());

        product.setCategory(category);
        return product;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

    public List<ProductDto> toDto(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }
}